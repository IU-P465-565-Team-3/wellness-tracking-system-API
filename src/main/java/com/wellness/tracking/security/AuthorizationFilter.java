package com.wellness.tracking.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    private static final String LOGIN_PATH = "/api/login";

    private final JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.cookieName}")
    private static String cookieName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals(LOGIN_PATH)) {
            filterChain.doFilter(request, response);
            return;
        }
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            try {
                String jwtToken = authorizationHeader.substring(BEARER.length());
                String username = "";
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                    if ("accessCookie".equals(cookie.getName())) {
                        String accessToken = cookie.getValue();
                        if (accessToken != null) {
                            username = jwtTokenUtil.validateTokenAndFetchUsername(accessToken);
                        } else {
                            username = null;
                        }

                    }
                }

                if (username != null) {
                    final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                }
            } catch (Exception exception) {
                response.sendError(FORBIDDEN.value(), exception.getMessage());
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
