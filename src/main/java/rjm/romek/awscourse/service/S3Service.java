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

        if (bucketLifecycleConfiguration == null) {
            return false;
        }

        final List<BucketLifecycleConfiguration.Rule> rules = bucketLifecycleConfiguration.getRules();

        for (BucketLifecycleConfiguration.Rule rule : rules) {
            List<BucketLifecycleConfiguration.Transition> ruleTransitions = rule.getTransitions();
            if (ruleTransitions.stream().filter(t -> transitionIn(t, transitions)).count() == transitions.size()) {
                return true;
            }
        }

        return false;
    }

    private boolean transitionIn(
            BucketLifecycleConfiguration.Transition t1,
            List<BucketLifecycleConfiguration.Transition> transitions
    ) {
        for (BucketLifecycleConfiguration.Transition t2 : transitions) {
            if (t1.getStorageClassAsString().equals(t2.getStorageClassAsString())
                    && t1.getDays() == t2.getDays()) {
                return true;
            }
        }

        return false;
    }
}
