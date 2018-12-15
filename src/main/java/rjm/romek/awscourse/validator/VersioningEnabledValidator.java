package rjm.romek.awscourse.validator;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.service.S3Service;

@Component
public class VersioningEnabledValidator implements TaskValidator {

    @Autowired
    private S3Service s3Service;

    @Override
    public Boolean isCompleted(UserTask userTask) {
        Map<String, String> answers = userTask.getAnswers();
        return s3Service.versioningEnabled(answers.getOrDefault("bucketName", ""));
    }
}
