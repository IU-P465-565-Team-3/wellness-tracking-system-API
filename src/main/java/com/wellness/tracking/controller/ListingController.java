package com.wellness.tracking.controller;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.wellness.tracking.dto.ListingDTO;
import com.wellness.tracking.dto.mapper.ListingMapper;
import com.wellness.tracking.model.*;
import com.wellness.tracking.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
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
    final PublicUserRepository publicUserRepository;
    final EnrollmentRepository enrollmentRepository;

    @GetMapping("/listing")
    public ResponseEntity<List<ListingSummary>> getAllListings(@RequestParam(required = false) String q) {
        List<ListingSummary> listings = new ArrayList<>();
        if (q == null) {
            listingSummaryRepository.findAllByIsPrivateIsFalse().forEach(listings::add);
        } else {
            listingSummaryRepository.findByDescriptionIgnoreCaseContainingAndIsPrivateIsFalse(q).forEach(listings::add);
            listingSummaryRepository.findByUserIgnoreCaseIn(publicUserRepository.findByFirstNameContaining(q)).forEach(listings::add);
        }
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @PostMapping("/listing")
    public ResponseEntity createListing(@RequestBody ListingDTO listingDTO) {
        try {
            PublicUser currentUser = getCurrentUser();
            listingDTO.setUser(currentUser);
            listingDTO.setIsPrivate(false);
            Listing listing = ListingMapper.toListing(listingDTO);
            listingRepository.save(listing);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listing/{id}")
    public ResponseEntity<ListingDTO> getListingById(@PathVariable("id") long id) {
        Optional<Listing> listingData = listingRepository.findById(id);

        if (listingData.isPresent()){
            ListingDTO listingDto = new ListingMapper<Long>().toListingDTO(listingData.get());
            return new ResponseEntity<>(listingDto, HttpStatus.OK);
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
