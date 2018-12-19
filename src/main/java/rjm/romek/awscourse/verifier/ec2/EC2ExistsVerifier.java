package rjm.romek.awscourse.verifier.ec2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.service.EC2Service;
import rjm.romek.awscourse.verifier.TaskVerifier;

@Service
public class EC2ExistsVerifier implements TaskVerifier {

    @Autowired
    private EC2Service ec2Service;

    @Override
    public Boolean isCompleted(UserTask userTask) {
        return false;
    }
}
