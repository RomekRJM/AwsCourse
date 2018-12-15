package rjm.romek.awscourse.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
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

    public boolean anyLifecyclePolicyMatches(String bucketName, List<BucketLifecycleConfiguration.Transition> transitions) {
        BucketLifecycleConfiguration bucketLifecycleConfiguration = s3Client.getBucketLifecycleConfiguration(bucketName);
        final List<BucketLifecycleConfiguration.Rule> rules = bucketLifecycleConfiguration.getRules();

        for (BucketLifecycleConfiguration.Rule rule : rules) {
            List<BucketLifecycleConfiguration.Transition> ruleTransitions = rule.getTransitions();
            int matching = 0;

            if (ruleTransitions.stream().anyMatch(t -> transitionIn(t, transitions))) {
                ++matching;
            }

            if (matching == transitions.size()) {
                return true;
            }
        }

        return false;
    }

    private boolean transitionIn(
            BucketLifecycleConfiguration.Transition t1,
            List<BucketLifecycleConfiguration.Transition> transitions
    ) {
        if (t1 == null) {
            return false;
        }

        for (BucketLifecycleConfiguration.Transition t2 : transitions) {
            if (t1 == t2) {
                return true;
            }

            if (t1.getStorageClassAsString().equals(t2.getStorageClassAsString())
                    && t2.getDays() == t2.getDays()) {
                return true;
            }
        }

        return false;
    }
}
