package com.wellness.tracking.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer";
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
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Optional<String> accessToken = Arrays.stream(request.getCookies())
                    .filter(cookie->"accessCookie".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findAny();

            if (accessToken.isPresent() && accessToken.get().startsWith(BEARER)) {
                try {
                    String jwtToken = accessToken.get().substring(BEARER.length());
                    String username = jwtTokenUtil.validateTokenAndFetchUsername(jwtToken);
                    Collection<SimpleGrantedAuthority> userRoles = new ArrayList<>();
                    userRoles.add(new SimpleGrantedAuthority(jwtTokenUtil.validateTokenAndFetchClaims(jwtToken)));
                    if (username != null) {
                        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, userRoles);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        filterChain.doFilter(request, response);
                    }
                } catch (Exception exception) {
                    response.sendError(FORBIDDEN.value(), exception.getMessage());
                }
            } else {
                filterChain.doFilter(request, response);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
