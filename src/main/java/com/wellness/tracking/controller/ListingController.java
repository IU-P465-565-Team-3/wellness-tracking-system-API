package com.wellness.tracking.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.wellness.tracking.model.Listing;
import com.wellness.tracking.model.ListingSummary;
import com.wellness.tracking.model.PublicUser;
import com.wellness.tracking.repository.ListingRepository;

import com.wellness.tracking.repository.ListingSummaryRepository;
import com.wellness.tracking.repository.PublicUserRepository;
import com.wellness.tracking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ListingController {

    final ListingRepository listingRepository;
    final ListingSummaryRepository listingSummaryRepository;
    final UserRepository userRepository;
    final PublicUserRepository publicUserRepository;

    @GetMapping("/listing")
    public ResponseEntity<List<ListingSummary>> getAllListings(@RequestParam(required = false) String q) {
        List<ListingSummary> listings = new ArrayList<>();
        if (q == null) {
            listingSummaryRepository.findAllByIsPrivateIsFalse().forEach(listings::add);
        } else {
            listingSummaryRepository.findByDescriptionContainingAndIsPrivateIsFalse(q).forEach(listings::add);
            listingSummaryRepository.findByUserIn(userRepository.findByFirstNameContaining(q)).forEach(listings::add);
        }
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @PostMapping("/listing")
    public ResponseEntity createListing(@RequestBody Listing listing) {
        try {
            PublicUser currentUser = getCurrentUser();
            listing.setUser(currentUser);
            listing.setIsPrivate(false);
            listingRepository.save(listing);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listing/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable("id") long id) {
        Optional<Listing> listingData = listingRepository.findById(id);

        if (listingData.isPresent()){
            return new ResponseEntity<>(listingData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/listing/{id}")
    public ResponseEntity<HttpStatus> deleteListing(@PathVariable("id") long id) {
        try {
            listingRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public PublicUser getCurrentUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return publicUserRepository.findUserByUsername(username);
    }
}
