package com.wellness.tracking.controller;

import com.wellness.tracking.model.Listing;
import com.wellness.tracking.model.ListingSummary;
import com.wellness.tracking.model.PublicUser;
import com.wellness.tracking.model.User;
import com.wellness.tracking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RecommendationsController {

    final ListingSummaryRepository listingSummaryRepository;
    final ListingRepository listingRepository;
    final UserRepository userRepository;
    final PublicUserRepository publicUserRepository;
    final AgeGroupRepository ageGroupRepository;
    final EnrollmentRepository enrollmentRepository;

    @GetMapping("/recommendations")
    public ResponseEntity<List<ListingSummary>> getRecommendations() {
        List<ListingSummary> listings = new ArrayList<>();
        listingSummaryRepository.findAllByIsPrivateIsFalse().forEach(listings::add);
        listingSummaryRepository.findByUser(getCurrentPublicUser()).forEach(listings::remove);
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @GetMapping("/recommendationsByAgeGroup")
    public ResponseEntity<List<Listing>> getRecommendationsByAgeGroup() {
        User user = getCurrentUser();
        Set<Long> listingsId = new HashSet<>();
        List<Listing> listings = new ArrayList<>();

        ageGroupRepository.findByAgeGroup(user.getAgeGroup()).getEnrollments().forEach(e -> {
            listingsId.add(e.getPlan().getId());
        });
        listingRepository.findByIdIn(listingsId).forEach(listings::add);

        listingsId.clear();
        enrollmentRepository.findAllByUser(getCurrentPublicUser()).forEach(e -> {
            listingsId.add(e.getPlan().getId());
        });

        listingRepository.findByIdIn(listingsId).forEach(listings::remove);
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    public User getCurrentUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findUserByUsername(username);
    }

    public PublicUser getCurrentPublicUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return publicUserRepository.findUserByUsername(username);
    }
}
