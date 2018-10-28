package rjm.romek.awscourse.service;

import org.springframework.stereotype.Service;

@Service
public class S3Service {
    public boolean bucketExists(String bucketName) {
        return bucketName != null;
    }
}
