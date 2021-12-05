package com.wellness.tracking.controller;

import com.wellness.tracking.model.Enrollment;
import com.wellness.tracking.model.FitnessPlan;
import com.wellness.tracking.model.PublicUser;
import com.wellness.tracking.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SubscriptionController {

    @PostMapping("/enrollment")
    public ResponseEntity createSubscriptions(@RequestBody User user, User publisher) {
        try {

            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}