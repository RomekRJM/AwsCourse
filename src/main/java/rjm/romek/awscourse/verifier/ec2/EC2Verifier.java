package rjm.romek.awscourse.verifier.ec2;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.amazonaws.services.ec2.model.Instance;

import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.service.EC2Service;
import rjm.romek.awscourse.verifier.TaskVerifier;

public abstract class EC2Verifier implements TaskVerifier {

    private EC2Service ec2Service;
    private String attributeName;
    public static final String INSTANCE_ID = "instanceId";

    public EC2Verifier(EC2Service ec2Service, String attributeName) {
        this.ec2Service = ec2Service;
        this.attributeName = attributeName;
    }

    @Override
    public Boolean isCompleted(UserTask userTask) {
        Map<String, String> answers = userTask.getAnswers();
        Optional<Instance> instance = ec2Service.getInstance(answers.getOrDefault(INSTANCE_ID, ""));

        if (instance.isPresent()) {
            return done(userTask, instance.get());
        }

        return false;
    }

    protected abstract String getEC2AttributeValue(Instance instance);

    protected boolean done(UserTask userTask, Instance instance) {
        return StringUtils.equals(getEC2AttributeValue(instance), getAttributeValue(userTask.getTask()));
    }

    private String getAttributeValue(Task task) {
        return task.getDescriptionFragments()
                .stream().filter(x -> StringUtils.equals(attributeName, x.getText()))
                .findFirst().get().getValue();
    }
}
