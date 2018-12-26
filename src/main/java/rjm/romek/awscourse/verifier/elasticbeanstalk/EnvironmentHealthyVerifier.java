package rjm.romek.awscourse.verifier.elasticbeanstalk;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.service.ElasticBeanstalkService;
import rjm.romek.awscourse.verifier.TaskVerifier;

@Service
public class EnvironmentHealthyVerifier implements TaskVerifier {

    @Autowired
    private ElasticBeanstalkService elasticBeanstalkService;

    @Override
    public Boolean isCompleted(UserTask userTask) {
        Map<String, String> answers = userTask.getAnswers();
        return elasticBeanstalkService.environmentHealthy(answers.getOrDefault("environmentName", ""));
    }
}
