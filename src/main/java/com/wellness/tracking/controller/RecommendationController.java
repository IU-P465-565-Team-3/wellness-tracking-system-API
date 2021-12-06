package com.wellness.tracking.controller;

import com.wellness.tracking.model.*;
import com.wellness.tracking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Date;
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RecommendationController {

    final ListingSummaryRepository listingSummaryRepository;
    final ListingRepository listingRepository;
    final UserRepository userRepository;
    final PublicUserRepository publicUserRepository;
    final AgeGroupRepository ageGroupRepository;
    final EnrollmentRepository enrollmentRepository;
    final SubscriptionRepository subscriptionRepository;

    @GetMapping("/recommendations")
    public ResponseEntity<List<ListingSummary>> getRecommendations() {
        List<ListingSummary> listings = new ArrayList<>();
        listingSummaryRepository.findAllByIsPrivateIsFalse().forEach(listings::add);
        listingSummaryRepository.findByUser(getCurrentPublicUser()).forEach(listings::remove);
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @GetMapping("/recommendation/listing/{listingId}")
    public ResponseEntity<List<ListingSummary>> getTagListingRecommendations(@PathVariable Long listingId) {
       Optional<Listing> listingSource = listingRepository.findById(listingId);
       PublicUser currentUser = getCurrentPublicUser();
       if (!listingSource.isPresent()) {
           return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
       }

       Set<Long> enrolledListingIds = new HashSet<>();
       enrollmentRepository.findAllByUser(currentUser).stream()
               .map(enrollment -> enrollment.getListing().getId())
               .forEach(enrolledListingIds::add);

       Set<Tag> tags = listingSource.get().getTags();
       Set<Long> similarListingIds = new HashSet<>();
       for (Tag tag : tags) {
           tag.getListings().stream().map(listing -> listingId).forEach(similarListingIds::add);
       }
       similarListingIds.removeAll(enrolledListingIds);

       List<ListingSummary> recommendations = listingSummaryRepository
               .findAllByIdInAndIsPrivateIsFalse(similarListingIds);
       recommendations = recommendations.subList(0, Math.min(30, recommendations.size()));
       Collections.shuffle(recommendations);
       return new ResponseEntity<>(recommendations, HttpStatus.OK);
    }

    @GetMapping("/recommendation/subscription")
    public ResponseEntity<List<ListingSummary>> getSubscriptionListingRecommendations() {
        PublicUser currentUser = getCurrentPublicUser();
        Set<PublicUser> subscribedProducers = new HashSet<>();
        subscriptionRepository.findAllByConsumer(currentUser).stream()
                .map(subscription -> subscription.getProducer())
                .forEach(subscribedProducers::add);

        Set<Long> enrolledListingIds = new HashSet<>();
        enrollmentRepository.findAllByUser(currentUser).stream()
                .map(enrollment -> enrollment.getListing().getId())
                .forEach(enrolledListingIds::add);

        List<ListingSummary> recommendations = listingSummaryRepository.findByUserInAndIdNotInAndIsPrivateIsFalse(subscribedProducers, enrolledListingIds);
        recommendations = recommendations.subList(0, Math.min(30, recommendations.size()));
        Collections.shuffle(recommendations);
        return new ResponseEntity<>(recommendations, HttpStatus.OK);
    }

    @GetMapping("recommendation/demographic")
    public ResponseEntity<List<ListingSummary>> getDemographicRecommendations() {
        PublicUser currentUser = getCurrentPublicUser();
        String gender = currentUser.getGender();
        Long dob = currentUser.getDateOfBirth().getTime();
        final Long FIVE_YEARS = 5L * 365L * 24L * 60L * 60L * 1000L;
        Date minAge = new java.sql.Date(dob - FIVE_YEARS);
        Date maxAge = new java.sql.Date(dob + FIVE_YEARS);
        List<PublicUser> similarUsers = publicUserRepository.findByGenderIsAndDateOfBirthBetween(gender, minAge, maxAge);
        Set<Long> similarListingIds = new HashSet<>();
        enrollmentRepository.findAllByUserIn(similarUsers)
                .stream()
                .map(enrollment -> enrollment.getListing().getId())
                .forEach(similarListingIds::add);

        Set<Long> enrolledListingIds = new HashSet<>();
        enrollmentRepository.findAllByUser(currentUser).stream()
                .map(enrollment -> enrollment.getUser().getId())
                .forEach(enrolledListingIds::add);

        List<ListingSummary> recommendations = listingSummaryRepository.findByIdInAndIdNotInAndIsPrivateIsFalse(similarListingIds, enrolledListingIds);
        recommendations = recommendations.subList(0, Math.min(30, recommendations.size()));
        Collections.shuffle(recommendations);
        return new ResponseEntity<>(recommendations, HttpStatus.OK);
    }

    @GetMapping("/recommendationsByAgeGroup")
    public ResponseEntity<List<Listing>> getRecommendationsByAgeGroup() {
        User user = getCurrentUser();
        Set<Long> listingsId = new HashSet<>();
        List<Listing> listings = new ArrayList<>();

        ageGroupRepository.findByAgeGroup(user.getAgeGroup()).getEnrollments().forEach(e -> {
            listingsId.add(e.getListing().getId());
        });
        listingRepository.findByIdIn(listingsId).forEach(listings::add);

        listingsId.clear();
        enrollmentRepository.findAllByUser(getCurrentPublicUser()).forEach(e -> {
            listingsId.add(e.getListing().getId());
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
