package com.wellness.tracking.repository;

import com.wellness.tracking.model.Enrollment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>{
    Enrollment findEnrollmentById(Long id);
}
