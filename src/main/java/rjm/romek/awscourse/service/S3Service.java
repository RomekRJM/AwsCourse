package rjm.romek.awscourse.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.BucketVersioningConfiguration;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 s3Client;

    public boolean bucketExists(String bucketName) {
        return s3Client.doesBucketExistV2(bucketName);
    }

    public boolean keyExists(String bucketName, String keyName) {
        return s3Client.doesObjectExist(bucketName, keyName);
    }

    public boolean versioningEnabled(String bucketName) {
        return StringUtils.equals(
                s3Client.getBucketVersioningConfiguration(bucketName).getStatus(),
                BucketVersioningConfiguration.ENABLED
        );
    }
}
