package com.wellness.tracking.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.wellness.tracking.model.Listing;
import com.wellness.tracking.model.ListingSummary;
import com.wellness.tracking.repository.ListingRepository;

import com.wellness.tracking.repository.ListingSummaryRepository;
import com.wellness.tracking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ListingController {

    final ListingRepository listingRepository;
    final ListingSummaryRepository listingSummaryRepository;
    final UserRepository userRepository;

    @GetMapping("/listing")
    public ResponseEntity<List<ListingSummary>> getAllListings(@RequestParam(required = false) String q){
        List<ListingSummary> listings = new ArrayList<>();
        if (q == null) {
            listingSummaryRepository.findAllByIsPrivateIsFalse().forEach(listings::add);
        } else {
            listingSummaryRepository.findByDescriptionContainingAndIsPrivateIsFalse(q).forEach(listings::add);
            listingSummaryRepository.findByUserIn(userRepository.findByfirstNameContaining(q)).forEach(listings::add);
        }
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @GetMapping("/listing/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable("id") long id){
        Optional<Listing> listingData = listingRepository.findById(id);

        if (listingData.isPresent()){
            return new ResponseEntity<>(listingData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/listing/{id}")
    public ResponseEntity<HttpStatus> deleteListing(@PathVariable("id") long id){
        try {
            listingRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
