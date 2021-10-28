package com.wellness.tracking.repository;

import com.wellness.tracking.model.ListingSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingSummaryRepository extends JpaRepository<ListingSummary, Long>, JpaSpecificationExecutor<ListingSummary> {
    List<ListingSummary> findAllByIsPrivateIsFalse();
}
