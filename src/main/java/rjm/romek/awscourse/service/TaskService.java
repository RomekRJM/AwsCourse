package rjm.romek.awscourse.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rjm.romek.awscourse.SpringContext;
import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.repository.TaskRepository;
import rjm.romek.awscourse.validator.TaskValidator;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Boolean checkTaskAndSaveAnswer(Task task, String answer) {

        if (task.getDone()) {
            return Boolean.TRUE;
        }

        final Class<? extends TaskValidator> validatorClass = task.getValidator();
        TaskValidator taskValidator = null;

        try {
            taskValidator = SpringContext.getAppContext().getBean(validatorClass);
        } catch (BeansException e) {
            throw new RuntimeException(e);
        }

        task.setAnswer(answer);
        Boolean done = taskValidator.isCompleted(task);
        task.setDone(done);
        taskRepository.save(task);

        return done;
    }
}
