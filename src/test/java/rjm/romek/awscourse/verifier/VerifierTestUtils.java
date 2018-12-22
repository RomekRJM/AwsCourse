package rjm.romek.awscourse.verifier;

import java.util.Map;

import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.model.UserTask;

public class VerifierTestUtils {
    public static UserTask createUserTask(String description, Map<String, String> answers) {
        Task task = new Task();
        task.setDescription(description);
        UserTask userTask = new UserTask();
        userTask.setAnswers(answers);
        userTask.setTask(task);

        return userTask;
    }
}
