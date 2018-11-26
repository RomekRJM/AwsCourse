package rjm.romek.awscourse.validator;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.service.S3Service;

@Component
public class KeyExistsValidator implements TaskValidator {

    @Autowired
    private S3Service s3Service;

    @Override
    public Boolean isCompleted(Task task) {
        Map<String, String> answers = task.getAnswers();
        return s3Service.keyExists(answers.getOrDefault("bucketName", ""),
                answers.getOrDefault("keyName", ""));
    }

}
