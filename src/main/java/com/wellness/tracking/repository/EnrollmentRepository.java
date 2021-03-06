package com.wellness.tracking.repository;

import com.wellness.tracking.model.Enrollment;

import com.wellness.tracking.model.FitnessPlan;
import com.wellness.tracking.model.Listing;
import com.wellness.tracking.model.PublicUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>{
    List<Enrollment> findAllByUserIn(Collection<PublicUser> users);
    List<Enrollment> findAllByUser(PublicUser user);
    Long countByListingAndUser(Listing listing, PublicUser user);
}
