package rjm.romek.awscourse.verifier.iam;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.service.AwsAssumedService;
import rjm.romek.awscourse.service.StsService;

public class CreateVolumeUpToSizeWithTypeVerifier extends CreateVolumeVerifier {

    @Autowired
    public CreateVolumeUpToSizeWithTypeVerifier(StsService stsService, AwsAssumedService awsAssumedService) {
        super(stsService, awsAssumedService);
    }

    @Override
    public Boolean isCompleted(UserTask userTask) {
        Map<String, String> parameters = userTask.getTask().getParametersFromDescription();
        Integer allowedSize = Integer.valueOf(parameters.getOrDefault("maxSize", "100"));
        Integer disallowedSize = allowedSize + 1;

        return checkCreateVolume(userTask, allowedSize) &&
                !checkCreateVolume(userTask, disallowedSize);
    }
}
