package com.wellness.tracking.repository;

import java.util.List;

import com.wellness.tracking.model.Listing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long>{
    Listing findListingById(Long id);
}
