package rjm.romek.awscourse.verifier.iam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.service.AwsAssumedService;
import rjm.romek.awscourse.service.StsService;

@Service
public class CreateVolumeInRegionAllowedVerifier extends CreateVolumeVerifier {

    @Autowired
    public CreateVolumeInRegionAllowedVerifier(StsService stsService, AwsAssumedService awsAssumedService) {
        super(stsService, awsAssumedService);
    }

    @Override
    public Boolean isCompleted(UserTask userTask) {
        return checkCreateVolume(userTask);
    }
}
