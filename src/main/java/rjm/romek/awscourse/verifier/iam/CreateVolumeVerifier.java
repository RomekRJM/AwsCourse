package rjm.romek.awscourse.verifier.iam;

import java.util.Map;

import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.ec2.AmazonEC2;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.service.AwsAssumedService;
import rjm.romek.awscourse.service.EC2Service;
import rjm.romek.awscourse.service.StsService;
import rjm.romek.awscourse.verifier.TaskVerifier;

public abstract class CreateVolumeVerifier implements TaskVerifier {
    private final StsService stsService;
    private final AwsAssumedService awsAssumedService;

    public CreateVolumeVerifier(StsService stsService, AwsAssumedService awsAssumedService) {
        this.stsService = stsService;
        this.awsAssumedService = awsAssumedService;
    }

    protected boolean checkCreateVolume(UserTask userTask) {
        return checkCreateVolume(userTask, 8);
    }

    protected boolean checkCreateVolume(UserTask userTask, Integer volumeSize) {
        return checkCreateVolume(userTask, volumeSize, "gp2");
    }

    protected boolean checkCreateVolume(UserTask userTask, Integer volumeSize, String type) {
        Map<String, String> answers = userTask.getAnswers();
        String roleArn = answers.getOrDefault("roleArn", "");
        Map<String, String> parameters = userTask.getTask().getParametersFromDescription();
        String region = parameters.getOrDefault("region", "");
        EC2Service ec2Service = createEC2ServiceUsingAssumedRole(roleArn, region);

        return ec2Service.dryRunCreateVolume(region + "a", volumeSize);
    }

    private EC2Service createEC2ServiceUsingAssumedRole(String roleArn, String region) {
        BasicSessionCredentials basicSessionCredentials = stsService.assumeRole(roleArn);
        AmazonEC2 amazonEC2 = awsAssumedService.amazonEC2(basicSessionCredentials, region);
        return new EC2Service(amazonEC2);
    }
}
