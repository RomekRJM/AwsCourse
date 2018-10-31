package rjm.romek.awscourse.service;

import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.validator.TaskValidator;

class DummyTaskValidator implements TaskValidator {

    public DummyTaskValidator() {

    }

    @Override
    public Boolean isCompleted(Task task) {
        return Boolean.TRUE;
    }
}
