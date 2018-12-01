package rjm.romek.awscourse.service;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.validator.TaskValidator;

class FailingTaskValidator implements TaskValidator {

    public FailingTaskValidator() throws InstantiationException {
        throw new InstantiationException();
    }

    @Override
    public Boolean isCompleted(UserTask userTask) {
        return Boolean.FALSE;
    }
}
