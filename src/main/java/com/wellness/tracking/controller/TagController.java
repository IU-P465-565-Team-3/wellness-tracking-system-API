package com.wellness.tracking.controller;

import com.wellness.tracking.model.Tag;
import com.wellness.tracking.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TagController {

    private final TagRepository tagRepository;

    @GetMapping("/tag")
    public ResponseEntity<List<Tag>> getAllTags() {
        return new ResponseEntity<>(tagRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/tag")
    public ResponseEntity createTag(@RequestBody Tag tag) {
        tagRepository.save(tag);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/tag/{name}")
    public ResponseEntity<Tag> getTagByName(@PathVariable("name") String name) {
        return new ResponseEntity(tagRepository.findTagByName(name), HttpStatus.OK);
    }
}