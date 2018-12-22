package rjm.romek.awscourse.testutils;

import java.util.Map;

import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.model.UserTask;

public class TestUtils {
    public static UserTask createUserTask(String description, Map<String, String> answers) {
        Task task = new Task();
        task.setDescription(description);
        UserTask userTask = new UserTask();
        userTask.setAnswers(answers);
        userTask.setTask(task);

        return userTask;
    }
}
