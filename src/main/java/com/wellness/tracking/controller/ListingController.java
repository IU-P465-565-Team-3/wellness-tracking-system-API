package com.wellness.tracking.controller;

import java.util.List;
import java.util.Optional;

import com.wellness.tracking.model.Listing;
import com.wellness.tracking.model.ListingSummary;
import com.wellness.tracking.repository.ListingRepository;

import com.wellness.tracking.repository.ListingSummaryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ListingController {

    final ListingRepository listingRepository;
    final ListingSummaryRepository listingSummaryRepository;

    public ListingController(ListingRepository listingRepository, ListingSummaryRepository listingSummaryRepository) {
        this.listingRepository = listingRepository;
        this.listingSummaryRepository = listingSummaryRepository;
    }

    @GetMapping("/listing")
    public ResponseEntity<List<ListingSummary>> getAllListings(){
        try {
            List<ListingSummary> listings =  listingSummaryRepository.findAllByIsPrivateIsFalse();
            return new ResponseEntity<>(listings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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