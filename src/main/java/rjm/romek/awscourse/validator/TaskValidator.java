package rjm.romek.awscourse.validator;

import rjm.romek.awscourse.model.Task;

public interface TaskValidator {
    public Boolean isCompleted(Task task);
}
