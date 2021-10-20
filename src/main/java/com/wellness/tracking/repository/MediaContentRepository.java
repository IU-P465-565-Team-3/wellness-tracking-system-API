package com.wellness.tracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wellness.tracking.model.MediaContent;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaContentRepository extends JpaRepository<MediaContent, Long> {
    MediaContent findMediaContentById();
}
