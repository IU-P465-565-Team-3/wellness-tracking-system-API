package com.wellness.tracking.controller;

import com.wellness.tracking.dto.JwtResponse;
import com.wellness.tracking.dto.UserDTO;
import com.wellness.tracking.model.User;
import com.wellness.tracking.security.JwtTokenUtil;
import com.wellness.tracking.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private static final String REGISTER_PATH = "/register";
    private static final String LOGIN_PATH = "/login";
    private static final String DEFAULT_PATH = "/";
    private static final String USER_DETAILS_PATH = "/update/user_details";

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping(DEFAULT_PATH)
    public ResponseEntity<String> defaultPath() {
        return ResponseEntity.ok("Valid Token");
    }

    @PostMapping(REGISTER_PATH)
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.registerUser(userDTO));
    }

    @PostMapping(LOGIN_PATH)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDTO authenticationRequest) throws Exception {

        final Authentication auth = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return ResponseEntity.ok(new JwtResponse(jwtTokenUtil.generateToken(auth)));
    }

    @PutMapping(USER_DETAILS_PATH+"/{username}")
    public ResponseEntity<User> updateUser(@PathVariable("username") String username, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(username, user));
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
}
