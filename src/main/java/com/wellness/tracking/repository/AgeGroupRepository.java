package com.wellness.tracking.repository;

import com.wellness.tracking.enums.AgeGroupType;
import com.wellness.tracking.model.AgeGroup;
import com.wellness.tracking.model.Enrollment;
import com.wellness.tracking.model.FitnessPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgeGroupRepository extends JpaRepository<AgeGroup, Long> {

    AgeGroup findByAgeGroup(AgeGroupType ageGroup);
}
