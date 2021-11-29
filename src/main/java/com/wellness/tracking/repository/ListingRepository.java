package com.wellness.tracking.repository;

import com.wellness.tracking.model.Listing;
import com.wellness.tracking.model.PublicUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long>, JpaSpecificationExecutor<Listing> {
    @Override
    Optional<Listing> findById(Long aLong);
    List<Listing> findByIdIn(Set<Long> aLongList);
}
