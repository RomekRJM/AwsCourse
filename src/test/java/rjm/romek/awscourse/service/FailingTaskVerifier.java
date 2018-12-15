package rjm.romek.awscourse.service;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.verifier.TaskVerifier;

class FailingTaskVerifier implements TaskVerifier {

    public FailingTaskVerifier() throws InstantiationException {
        throw new InstantiationException();
    }

    @Override
    public Boolean isCompleted(UserTask userTask) {
        return Boolean.FALSE;
    }
}
