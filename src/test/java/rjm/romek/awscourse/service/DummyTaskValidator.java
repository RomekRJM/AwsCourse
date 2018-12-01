package rjm.romek.awscourse.service;

import org.springframework.stereotype.Component;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.validator.TaskValidator;

@Component
class DummyTaskValidator implements TaskValidator {

    public DummyTaskValidator() {

    }

    @Override
    public Boolean isCompleted(UserTask userTask) {
        return Boolean.TRUE;
    }
}
