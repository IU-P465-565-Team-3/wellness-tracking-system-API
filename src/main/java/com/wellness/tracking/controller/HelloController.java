package com.wellness.tracking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private static final String DEFAULT_PATH = "/";

    @GetMapping(DEFAULT_PATH)
    public ResponseEntity<String> defaultPath() {
        return ResponseEntity.ok("Hello from P465/565 Team 3!");
    }
}
