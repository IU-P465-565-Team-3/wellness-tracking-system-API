package com.wellness.tracking.controller;

import java.util.*;

import com.wellness.tracking.dto.ListingDTO;
import com.wellness.tracking.dto.mapper.ListingMapper;
import com.wellness.tracking.model.*;
import com.wellness.tracking.repository.*;

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
    final PublicUserRepository publicUserRepository;
    final EnrollmentRepository enrollmentRepository;
    final TagRepository tagRepository;

    @GetMapping("/listing")
    public ResponseEntity<Set<ListingSummary>> getAllListings(@RequestParam(required = false) String q,
                                                               @RequestParam(required = false) String publisher,
                                                               @RequestParam(required = false) List<String> tags) {
        Set<ListingSummary> listings = new LinkedHashSet<>();
        List<PublicUser> publishers = new ArrayList<>();
        if (publisher != null) {
            publicUserRepository.findByFirstNameContainingOrLastNameContaining(publisher, publisher).forEach(publishers::add);
        }
        List<Tag> listingTags = new ArrayList<>();
        if (tags != null && tags.size() > 0) {
            tagRepository.findTagByNameIn(tags).forEach(listingTags::add);
        }
        if (q == null && publisher == null && (tags == null || tags.size() == 0)) {
            listingSummaryRepository.findAllByIsPrivateIsFalse().forEach(listings::add);
        } else if (publisher == null && (tags == null || tags.size() == 0)) {
            listingSummaryRepository.findByNameContainingOrDescriptionContainingAndIsPrivateIsFalse(q, q).forEach(listings::add);
        } else if (q == null &&  (tags == null || tags.size() == 0)) {
            listingSummaryRepository.findByUserInAndIsPrivateIsFalse(publishers).forEach(listings::add);
        } else if (q == null && publisher == null) {
            listingSummaryRepository.findByTagsInAndIsPrivateIsFalse(listingTags).forEach(listings::add);
        } else if (tags == null || tags.size() == 0) {
            listingSummaryRepository.findByNameContainingOrDescriptionContainingAndUserInAndIsPrivateIsFalse(q, q, publishers).forEach(listings::add);
        } else if (publisher == null) {
            listingSummaryRepository.findByNameContainingOrDescriptionContainingAndTagsInAndIsPrivateIsFalse(q, q, listingTags).forEach(listings::add);
        } else if (q == null) {
            listingSummaryRepository.findByUserInAndTagsInAndIsPrivateIsFalse(publishers, listingTags).forEach(listings::add);
        } else {
            listingSummaryRepository.findByNameContainingAndUserInAndTagsInAndIsPrivateIsFalse(q, publishers, listingTags).forEach(listings::add);
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
            List<Tag> tags = listing.getTags();
            if (tags != null) {
                for (int i = 0; i < tags.size(); i++) {
                    Tag currentTag = tags.get(i);
                    if (currentTag.getId() != null) {
                        Optional<Tag> tagEntity = tagRepository.findById(currentTag.getId());
                        if (!tagEntity.isPresent()) {
                            continue;
                        }
                        tags.set(i, tagEntity.get());
                    }
                }
            }
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
