package rjm.romek.awscourse.verifier.rds;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.amazonaws.services.rds.model.DBInstance;

import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.service.RDSService;
import rjm.romek.awscourse.verifier.TaskVerifier;

public abstract class RDSVerifier implements TaskVerifier {

    protected final RDSService rdsService;
    private String attributeName;
    public static final String DB_INSTANCE_ID = "dbInstanceId";

    public RDSVerifier(RDSService rdsService, String attributeName) {
        this.rdsService = rdsService;
        this.attributeName = attributeName;
    }

    @Override
    public Boolean isCompleted(UserTask userTask) {
        Map<String, String> answers = userTask.getAnswers();
        Optional<DBInstance> dbInstance = rdsService.getDBInstance(answers.getOrDefault(DB_INSTANCE_ID, ""));

        if (dbInstance.isPresent()) {
            return done(userTask, dbInstance.get());
        }

        return false;
    }

    protected abstract Object getDBAttributeValue(DBInstance instance);

    protected boolean done(UserTask userTask, DBInstance dbInstance) {
        Object ec2AttributeValue = getDBAttributeValue(dbInstance);

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
