package rjm.romek.awscourse.verifier.ec2;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.service.EC2Service;
import rjm.romek.awscourse.verifier.TaskVerifier;

@Service
public class EC2ExistsVerifier implements TaskVerifier {

    @Autowired
    private EC2Service ec2Service;

    @Override
    public Boolean isCompleted(UserTask userTask) {
        Map<String, String> answers = userTask.getAnswers();
        return ec2Service.ec2InstanceUpAndIsOfType(answers.getOrDefault("instanceId", ""),
                getInstanceType(userTask.getTask()));
    }

    private String getInstanceType(Task task) {
        return task.getDescriptionFragments()
                .stream().filter(x -> EC2Service.INSTANCE_TYPE.equals(x.getText()))
                .findFirst().get().getValue();
    }
}
