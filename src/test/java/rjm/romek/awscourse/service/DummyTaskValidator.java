package rjm.romek.awscourse.service;

import org.springframework.stereotype.Component;

import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.validator.TaskValidator;

@Component
class DummyTaskValidator implements TaskValidator {

    public DummyTaskValidator() {

    }

    @Override
    public Boolean isCompleted(Task task) {
        return Boolean.TRUE;
    }
}
