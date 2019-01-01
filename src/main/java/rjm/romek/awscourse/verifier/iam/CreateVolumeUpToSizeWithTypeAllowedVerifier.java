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
            if (StringUtils.contains(deniedTypes, type)) {
                if (checkCreateVolume(userTask, disallowedSize, type)) {
                    return false;
                }
            } else {
                if (!checkCreateVolume(userTask, allowedSize, type)) {
                    return false;
                }
                if (checkCreateVolume(userTask, disallowedSize, type)) {
                    return false;
                }
            }
        }

        return true;
    }
}
