package rjm.romek.awscourse.verifier;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.service.InstanceService;

public abstract class InstanceVerifier<T> implements TaskVerifier {

    protected InstanceService<T> service;
    private String attributeName;
    protected String instanceIdParam;

    public InstanceVerifier(InstanceService service, String instanceIdParam, String attributeName) {
        this.service = service;
        this.attributeName = attributeName;
        this.instanceIdParam = instanceIdParam;
    }

    @Override
    public Boolean isCompleted(UserTask userTask) {
        Map<String, String> answers = userTask.getAnswers();
        Optional<T> instance = service.getInstance(answers.getOrDefault(instanceIdParam, ""));

        if (instance.isPresent()) {
            return done(userTask, instance.get());
        }

        return false;
    }

    protected abstract Object getInstanceAttributeValue(T instance);

    protected boolean done(UserTask userTask, T instance) {
        Object ec2AttributeValue = getInstanceAttributeValue(instance);

        if (ec2AttributeValue instanceof String) {
            return StringUtils.equals((String)ec2AttributeValue, getAttributeValue(userTask.getTask()));
        }

        return false;
    }

    protected final String getAttributeValue(Task task) {
        return task.getDescriptionFragments()
                .stream().filter(x -> StringUtils.equals(attributeName, x.getText()))
                .findFirst().get().getValue();
    }
}