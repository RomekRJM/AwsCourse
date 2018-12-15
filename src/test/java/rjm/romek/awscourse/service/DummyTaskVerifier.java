package rjm.romek.awscourse.service;

import org.springframework.stereotype.Component;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.verifier.TaskVerifier;

@Component
class DummyTaskVerifier implements TaskVerifier {

    public DummyTaskVerifier() {

    }

    @Override
    public Boolean isCompleted(UserTask userTask) {
        return Boolean.TRUE;
    }
}
