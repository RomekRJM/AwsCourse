package rjm.romek.awscourse.verifier.iam;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.ec2.AmazonEC2;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.service.AwsAssumedService;
import rjm.romek.awscourse.service.EC2Service;
import rjm.romek.awscourse.service.StsService;
import rjm.romek.awscourse.verifier.TaskVerifier;

@Service
public class DescribeInstancesVerifier implements TaskVerifier {

    private final StsService stsService;
    private final AwsAssumedService awsAssumedService;

    @Autowired
    public DescribeInstancesVerifier(StsService stsService, AwsAssumedService awsAssumedService) {
        this.stsService = stsService;
        this.awsAssumedService = awsAssumedService;
    }

    @Override
    public Boolean isCompleted(UserTask userTask) {
        Map<String, String> answers = userTask.getAnswers();
        createEC2ServiceUsingAssumedRole(answers.getOrDefault("roleArn", "")).runDescribeInstances();
        return true;
    }

    private EC2Service createEC2ServiceUsingAssumedRole(String assumedRole) {
        BasicSessionCredentials basicSessionCredentials = stsService.assumeRole(assumedRole);
        AmazonEC2 amazonEC2 = awsAssumedService.amazonEC2(basicSessionCredentials);
        return new EC2Service(amazonEC2);
    }
}
