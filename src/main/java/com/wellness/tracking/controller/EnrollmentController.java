package com.wellness.tracking.controller;

import com.wellness.tracking.model.Enrollment;
import com.wellness.tracking.repository.EnrollmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EnrollmentController {
    
    @Autowired
    EnrollmentRepository enrollmentRepository;

    @PostMapping("/enrollment")
    public ResponseEntity<Enrollment> createEnrollment(@RequestBody Enrollment enrollment){
        try {
            Enrollment _enrollment = enrollmentRepository.save(new Enrollment());
            
            return new ResponseEntity<>(_enrollment, HttpStatus.CREATED);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
