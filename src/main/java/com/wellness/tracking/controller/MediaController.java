package com.wellness.tracking.controller;

import com.wellness.tracking.repository.S3Repository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MediaController {
    final S3Repository s3Repository;

    @PostMapping("/media")
    public ResponseEntity<String> uploadFile(@PathVariable MultipartFile file) {
        try {
            String tmpDir = System.getProperty("java.io.tmpdir");
            String fileName = UUID.randomUUID().toString().replace("-", "");
            Path filePath = Path.of(tmpDir, fileName);
            File tmpFile = new File(filePath.toString());
            file.transferTo(tmpFile);
            s3Repository.uploadObject(fileName, tmpFile);
            return new ResponseEntity<>(fileName, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Unable to upload.", HttpStatus.BAD_REQUEST);
        }
    }
}
