package rjm.romek.awscourse.verifier.s3;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.StorageClass;
import com.google.common.collect.ImmutableList;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.service.S3Service;
import rjm.romek.awscourse.verifier.TaskVerifier;

@Component
public class LifecyclePolicyVerifier implements TaskVerifier {

    @Autowired
    private S3Service s3Service;

    private static final BucketLifecycleConfiguration.Transition T1 =
            new BucketLifecycleConfiguration.Transition().withDays(30).withStorageClass(StorageClass.Standard);
    private static final BucketLifecycleConfiguration.Transition T2 =
            new BucketLifecycleConfiguration.Transition().withDays(90).withStorageClass(StorageClass.StandardInfrequentAccess);
    private static final BucketLifecycleConfiguration.Transition T3 =
            new BucketLifecycleConfiguration.Transition().withDays(240).withStorageClass(StorageClass.Glacier);

    private static final List<BucketLifecycleConfiguration.Transition> TRANSITION_LIST = ImmutableList.of(T1, T2, T3);

    @Override
    public Boolean isCompleted(UserTask userTask) {
        Map<String, String> answers = userTask.getAnswers();
        return s3Service.anyLifecyclePolicyMatches(answers.getOrDefault("bucketName", ""), TRANSITION_LIST);
    }
}
