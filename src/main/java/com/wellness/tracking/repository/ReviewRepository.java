package com.wellness.tracking.repository;

import com.wellness.tracking.model.PublicUser;
import com.wellness.tracking.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByListingIdAndUser(Long listingId, PublicUser user);
    List<Review> findByListingIdAndUserIsNotOrderByCreatedDateDesc(Long listingId, PublicUser user);
}
