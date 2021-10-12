package com.wellness.tracking.repository;

import com.wellness.tracking.model.FitnessPlan;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface FitnessPlanRepository extends JpaRepository<FitnessPlan, Long> {
}