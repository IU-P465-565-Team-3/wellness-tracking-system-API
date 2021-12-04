package com.wellness.tracking.controller;

import com.wellness.tracking.dto.JwtResponse;
import com.wellness.tracking.dto.UserDTO;
import com.wellness.tracking.enums.Role;
import com.wellness.tracking.model.Enrollment;
import com.wellness.tracking.model.ListingSummary;
import com.wellness.tracking.model.PublicUser;
import com.wellness.tracking.model.User;
import com.wellness.tracking.repository.PublicUserRepository;
import com.wellness.tracking.security.JwtTokenUtil;
import com.wellness.tracking.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private static final String REGISTER_PATH = "/register";
    private static final String LOGIN_PATH = "/login";
    private static final String DEFAULT_PATH = "/";
    public static final String BEARER = "Bearer";

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    private final PublicUserRepository publicUserRepository;

    @GetMapping(DEFAULT_PATH)
    public ResponseEntity<String> defaultPath() {
        return ResponseEntity.ok("Valid Token");
    }

    @PostMapping(REGISTER_PATH)
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO);
        return ResponseEntity.ok(null);
    }

    @PostMapping(LOGIN_PATH)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDTO authenticationRequest) throws Exception {
        final Authentication authResult = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authResult);
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            PublicUser userProfile = publicUserRepository.findUserByUsername(authenticationRequest.getUsername());
            String jwtToken = jwtTokenUtil.generateToken(authResult);
            ResponseCookie sessionCookie = ResponseCookie.from( "accessCookie", BEARER + jwtToken)
                    .httpOnly(true)
                    .secure(true)
                    .maxAge(60 * 60 * 24 * 14) // Set cookie to expire after two weeks
                    .path("/")
                    .sameSite("None")
                    .build();
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, sessionCookie.toString())
                    .body(userProfile);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    private Authentication authenticate(String username, String password) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @GetMapping("/getProfessionals")
    public ResponseEntity<List<PublicUser>> getProfessionals() {
        List<PublicUser> publicUsers = new ArrayList<>();
        publicUserRepository.findUserByRole(Role.PROFESSIONAL).forEach(publicUsers::add);
        return new ResponseEntity<>(publicUsers, HttpStatus.OK);
    }
}
