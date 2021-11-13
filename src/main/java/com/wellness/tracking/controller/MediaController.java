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

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            int extIdx = file.getOriginalFilename().lastIndexOf('.');
            String extension = extIdx != -1 ? file.getOriginalFilename().substring(extIdx) : "";
            String fileName = UUID.randomUUID().toString().replace("-", "") + extension;
            Path filePath = Paths.get(tmpDir, fileName);
            file.transferTo(filePath);
            URL objectUrl = s3Repository.uploadObject(fileName, filePath.toFile());
            return new ResponseEntity<>(objectUrl.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
    }
}
