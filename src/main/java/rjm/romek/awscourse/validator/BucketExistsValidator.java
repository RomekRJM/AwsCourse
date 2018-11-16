package rjm.romek.awscourse.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.service.S3Service;

@Component
public class BucketExistsValidator implements TaskValidator {

    @Autowired
    private S3Service s3Service;

    @Override
    public Boolean isCompleted(Task task) {
        return s3Service.bucketExists(task.getAnswer());
    }
}
