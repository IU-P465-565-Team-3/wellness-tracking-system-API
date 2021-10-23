package com.wellness.tracking.repository;

import java.util.List;

import com.wellness.tracking.model.Listing;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing, Long>{
    Listing findListingById(Long id);

    List<Listing> filterListingByIsPrivate(Boolean isPrivate);
}
