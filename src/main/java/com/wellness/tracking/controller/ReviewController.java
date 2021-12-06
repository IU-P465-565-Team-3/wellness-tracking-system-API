package com.wellness.tracking.controller;


import com.wellness.tracking.model.Listing;
import com.wellness.tracking.model.PublicUser;
import com.wellness.tracking.model.Review;
import com.wellness.tracking.repository.EnrollmentRepository;
import com.wellness.tracking.repository.ListingRepository;
import com.wellness.tracking.repository.PublicUserRepository;
import com.wellness.tracking.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    final PublicUserRepository publicUserRepository;
    final ListingRepository listingRepository;
    final ReviewRepository reviewRepository;
    final EnrollmentRepository enrollmentRepository;

    @PostMapping("/listing/{listingId}/review")
    public ResponseEntity<String> createReview(@PathVariable Long listingId, @RequestBody Review review) {
        Boolean exists = listingRepository.existsById(listingId);
        if (!exists) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        PublicUser currentUser = getCurrentPublicUser();
        review.setListingId(listingId);
        review.setUser(currentUser);
        reviewRepository.save(review);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @GetMapping("/listing/{listingId}/review/current")
    public ResponseEntity<Review> getCurrentUserListingReview(@PathVariable Long listingId) {
        Boolean exists = listingRepository.existsById(listingId);
        if (!exists) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        PublicUser currentUser = getCurrentPublicUser();
        Optional<Review> userReview = reviewRepository.findByListingIdAndUser(listingId, currentUser);
        if (!userReview.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(userReview.get(), HttpStatus.OK);
    }

    @GetMapping("/listing/{listingId}/review")
    public ResponseEntity<List<Review>> getListingReviewsExceptCurrent(@PathVariable Long listingId) {
        Boolean exists = listingRepository.existsById(listingId);
        if (!exists) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        PublicUser currentUser = getCurrentPublicUser();
        List<Review> listingReviews = reviewRepository.findByListingIdAndUserIsNotOrderByCreatedDateDesc(listingId, currentUser);

        return new ResponseEntity<>(listingReviews, HttpStatus.OK);
    }

    public PublicUser getCurrentPublicUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return publicUserRepository.findUserByUsername(username);
    }

}
