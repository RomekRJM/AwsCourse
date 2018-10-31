package rjm.romek.awscourse.service;

import org.springframework.stereotype.Service;

import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.validator.TaskValidator;

@Service
public class TaskService {

    public Boolean checkTask(Task task) {

        if (task.getDone()) {
            return Boolean.TRUE;
        }

        final Class<? extends TaskValidator> validatorClass = task.getValidator();
        TaskValidator taskValidator = null;

        try {
            taskValidator = validatorClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }

        return taskValidator.isCompleted(task);
    }
}
