package com.wellness.tracking.repository;

import com.wellness.tracking.model.ListingSummary;
import com.wellness.tracking.model.PublicUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingSummaryRepository extends JpaRepository<ListingSummary, Long>, JpaSpecificationExecutor<ListingSummary> {
    List<ListingSummary> findAllByIsPrivateIsFalse();
    List<ListingSummary> findByDescriptionIgnoreCaseContainingAndIsPrivateIsFalse(String q);
    List<ListingSummary> findByUserIgnoreCaseIn(List<PublicUser> user);
    List<ListingSummary> findByUser(PublicUser user);
}
