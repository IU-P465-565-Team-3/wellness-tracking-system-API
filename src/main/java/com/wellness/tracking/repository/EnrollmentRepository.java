package com.wellness.tracking.repository;

import com.wellness.tracking.model.Enrollment;

import com.wellness.tracking.model.PublicUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>{
    Enrollment findEnrollmentById(Long id);

    List<Enrollment> findAllByUser(PublicUser user);
}
