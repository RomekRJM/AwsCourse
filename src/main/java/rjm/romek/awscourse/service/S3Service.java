package rjm.romek.awscourse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 s3Client;

    public boolean bucketExists(String bucketName) {
        return s3Client.doesBucketExistV2(bucketName);
    }
}
