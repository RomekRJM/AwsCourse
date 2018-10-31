package rjm.romek.awscourse.service;

import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.validator.TaskValidator;

class FailingTaskValidator implements TaskValidator {

    public FailingTaskValidator() throws InstantiationException {
        throw new InstantiationException();
    }

    @Override
    public Boolean isCompleted(Task task) {
        return Boolean.FALSE;
    }
}
