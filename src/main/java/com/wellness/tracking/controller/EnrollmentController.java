package com.wellness.tracking.controller;

import com.wellness.tracking.model.Enrollment;
import com.wellness.tracking.model.FitnessPlan;
import com.wellness.tracking.model.Listing;
import com.wellness.tracking.model.PublicUser;
import com.wellness.tracking.repository.EnrollmentRepository;
import com.wellness.tracking.repository.ListingRepository;
import com.wellness.tracking.repository.PublicUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class EnrollmentController {

    final PublicUserRepository publicUserRepository;

    final ListingRepository listingRepository;

    final EnrollmentRepository enrollmentRepository;

    @GetMapping("/enrollment")
    public ResponseEntity<List<Enrollment>> getEnrollments() {
        try {
            PublicUser currentUser = getCurrentUser();
            List<Enrollment> enrollments = enrollmentRepository.findAllByUser(currentUser);
            return new ResponseEntity<>(enrollments, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    * Workflow for user to create and enroll to their own plan.
    * */
    @PostMapping("/enrollment")
    public ResponseEntity createEnrollment(@RequestBody Enrollment enrollment) {
        try {
            FitnessPlan plan = enrollment.getPlan();
            PublicUser currentUser = getCurrentUser();
            plan.setUser(currentUser);
            enrollment.setUser(currentUser);
            plan.setIsPrivate(true);
            enrollmentRepository.save(enrollment);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * Workflow for user to enroll in published plans.
     * */
    @PostMapping("/enrollment/{listingId}")
    public ResponseEntity createEnrollment(@RequestBody Enrollment enrollment, @PathVariable Long listingId) {
        try {
            Optional<Listing> listing = listingRepository.findById(listingId);
            if (!listing.isPresent()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            PublicUser currentUser = getCurrentUser();
            enrollment.setUser(currentUser);
            enrollment.setPlan((FitnessPlan) listing.get());
            enrollmentRepository.save(enrollment);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public PublicUser getCurrentUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return publicUserRepository.findUserByUsername(username);
    }
}
