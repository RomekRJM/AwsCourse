package rjm.romek.awscourse.verifier.s3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.StorageClass;

import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.service.S3Service;
import rjm.romek.awscourse.verifier.TaskVerifier;

@Component
public class LifecyclePolicyVerifier implements TaskVerifier {

    @Autowired
    private S3Service s3Service;

    @Override
    public Boolean isCompleted(UserTask userTask) {
        Map<String, String> answers = userTask.getAnswers();
        return s3Service.anyLifecyclePolicyMatches(answers.getOrDefault("bucketName", ""),
                buildTransitionList(userTask.getTask()));
    }

    private List<BucketLifecycleConfiguration.Transition> buildTransitionList(Task task) {
        Map<String, String> parameters = task.getParametersFromDescription();
        List<BucketLifecycleConfiguration.Transition> transitions = new ArrayList<>();

        for (StorageClass storageClass : StorageClass.values()) {
            String storageClassName = storageClass.toString();
            String days = parameters.get(storageClassName);

            if (days != null) {
                transitions.add(
                        new BucketLifecycleConfiguration.Transition()
                                .withStorageClass(storageClassName)
                                .withDays(Integer.valueOf(days))
                );
            }
        }

        return transitions;
    }
}
