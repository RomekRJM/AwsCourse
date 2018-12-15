package rjm.romek.awscourse.verifier;

import rjm.romek.awscourse.model.UserTask;

public interface TaskVerifier {
    public Boolean isCompleted(UserTask userTask);
}
