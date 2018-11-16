package rjm.romek.awscourse.service;

import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Service
public class S3Service {

    private final AmazonS3 s3Client;

    public S3Service() {
        s3Client = AmazonS3ClientBuilder.standard()
                .build();
    }

    public boolean bucketExists(String bucketName) {
        return s3Client.doesBucketExistV2(bucketName);
    }
}
