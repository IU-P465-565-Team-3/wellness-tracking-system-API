package com.wellness.tracking.controller;

import com.wellness.tracking.dto.EnrollmentDTO;
import com.wellness.tracking.dto.mapper.EnrollmentMapper;
import com.wellness.tracking.model.*;
import com.wellness.tracking.repository.EnrollmentRepository;
import com.wellness.tracking.repository.ListingRepository;
import com.wellness.tracking.repository.PublicUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<List<EnrollmentDTO>> getEnrollments() {
        try {
            PublicUser currentUser = getCurrentUser();
            List<Enrollment> enrollmentRecords = enrollmentRepository.findAllByUser(currentUser);
            List<EnrollmentDTO> enrollments = new ArrayList<>();
            EnrollmentMapper<Long> mapper = new EnrollmentMapper<>();
            enrollmentRecords.stream().map(record -> mapper.toEnrollmentDTO(record)).forEach(enrollments::add);
            return new ResponseEntity<>(enrollments, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listing/{listingId}/enrollment/count")
    public ResponseEntity<Long> getEnrollmentCount(@PathVariable Long listingId) {
        try {
            PublicUser currentUser = getCurrentUser();
            Optional<Listing> listing = listingRepository.findById(listingId);
            if (!listing.isPresent()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            Long enrollmentCount = enrollmentRepository.countByListingAndUser(listing.get(), currentUser);
            return new ResponseEntity<>(enrollmentCount, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    * Workflow for user to create and enroll to their own plan.
    * */
    @PostMapping("/enrollment")
    public ResponseEntity createEnrollment(@RequestBody EnrollmentDTO enrollmentDTO) {
        try {
            PublicUser currentUser = getCurrentUser();
            Enrollment enrollment = EnrollmentMapper.toEnrollment(enrollmentDTO);
            Listing listing = enrollment.getListing();
            listing.setIsPrivate(true);
            listing.setIsApproved(true);
            listing.setUser(currentUser);
            enrollment.setUser(currentUser);
            enrollmentRepository.save(enrollment);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * Workflow for user to enroll in published listings.
     * */
    @PostMapping("/enrollment/{listingId}")
    public ResponseEntity createEnrollment(@RequestBody EnrollmentDTO enrollmentDTO, @PathVariable Long listingId) {
        try {
            Optional<Listing> listing = listingRepository.findById(listingId);
            if (!listing.isPresent()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            PublicUser currentUser = getCurrentUser();
            Enrollment enrollment = EnrollmentMapper.toEnrollment(enrollmentDTO);
            enrollment.setUser(currentUser);
            enrollment.setListing(listing.get());
            enrollmentRepository.save(enrollment);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/enrollment")
    public ResponseEntity updateEnrollment(@RequestBody EnrollmentDTO enrollmentDTO) {
        try {
            Enrollment enrollment = EnrollmentMapper.toEnrollment(enrollmentDTO);
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
