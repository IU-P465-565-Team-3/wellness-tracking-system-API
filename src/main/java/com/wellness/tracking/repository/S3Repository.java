package com.wellness.tracking.repository;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.File;

@Configuration
class S3Config {
    @Bean
    public AmazonS3Client amazonS3Client(@Value("${cloud.aws.region.static}") String region) {
        return (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();
    }
}

@Service
public class S3Repository {
   private final AmazonS3Client s3Client;

   private final String bucketName;

   public S3Repository(@Qualifier("amazonS3Client") AmazonS3Client s3Client, @Value("${app.awsServices.bucketName}") String bucketName) {
       this.s3Client = s3Client;
       this.bucketName = bucketName;
   }

   public PutObjectResult uploadObject(String fileName, File file) {
       return s3Client.putObject(this.bucketName, fileName, file);
   }
}
