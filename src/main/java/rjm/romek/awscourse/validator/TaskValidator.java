package rjm.romek.awscourse.validator;

import rjm.romek.awscourse.model.UserTask;

public interface TaskValidator {
    public Boolean isCompleted(UserTask userTask);
}
