package rjm.romek.awscourse.service;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rjm.romek.awscourse.SpringContext;
import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.repository.UserTaskRepository;
import rjm.romek.awscourse.validator.TaskValidator;

@Service
public class UserTaskService {

    private UserTaskRepository userTaskRepository;

    @Autowired
    public UserTaskService(UserTaskRepository userTaskRepository) {
        this.userTaskRepository = userTaskRepository;
    }

    public Boolean checkTaskAndSaveAnswer(UserTask userTask, Map<String, String> answers) {
        final Class<? extends TaskValidator> validatorClass = userTask.getTask().getValidator();
        TaskValidator taskValidator = null;

        try {
            taskValidator = SpringContext.getAppContext().getBean(validatorClass);
        } catch (BeansException e) {
            throw new RuntimeException(e);
        }

        userTask.setAnswers(answers);
        Boolean done = taskValidator.isCompleted(userTask);
        userTask.setDone(done);
        userTaskRepository.save(userTask);

        return done;
    }
}
