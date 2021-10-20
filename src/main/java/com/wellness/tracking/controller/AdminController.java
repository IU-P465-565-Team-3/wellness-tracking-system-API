package com.wellness.tracking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private static final String DEFAULT_PATH = "/";

    @GetMapping(DEFAULT_PATH)
    public ResponseEntity<String> defaultPath() {
        return ResponseEntity.ok("Hello Admin");
    }
}
