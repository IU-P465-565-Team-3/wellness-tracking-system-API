package com.wellness.tracking.repository;

import com.wellness.tracking.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findTagByName(String name);
    List<Tag> findTagByNameIn(List<String> names);
}