package rjm.romek.awscourse.verifier.iam;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.ec2.model.VolumeType;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.service.AwsAssumedService;
import rjm.romek.awscourse.service.StsService;

@Service
public class CreateVolumeUpToSizeWithTypeAllowedVerifier extends CreateVolumeVerifier {

    @Autowired
    public CreateVolumeUpToSizeWithTypeAllowedVerifier(StsService stsService, AwsAssumedService awsAssumedService) {
        super(stsService, awsAssumedService);
    }

    @Override
    public Boolean isCompleted(UserTask userTask) {
        Map<String, String> parameters = userTask.getTask().getParametersFromDescription();
        Integer allowedSize = Integer.valueOf(parameters.getOrDefault("maxSize", "100"));
        Integer disallowedSize = allowedSize + 1;
        String deniedTypes = parameters.getOrDefault("deniedTypes", "");

        for (VolumeType volumeType : VolumeType.values()) {
            String type = volumeType.toString();
            boolean deniedType = StringUtils.contains(deniedTypes, type);
            boolean createdOnAllowedSize = checkCreateVolume(userTask, allowedSize, type);
            boolean createdOnDisallowedSize = checkCreateVolume(userTask, disallowedSize, type);
            boolean isCorrect;

            if(deniedType) {
                isCorrect = createdOnAllowedSize && !createdOnDisallowedSize;
            } else {
                isCorrect = createdOnAllowedSize && createdOnDisallowedSize;
            }

            if (!isCorrect) {
                return false;
            }
        }

        return true;
    }
}
