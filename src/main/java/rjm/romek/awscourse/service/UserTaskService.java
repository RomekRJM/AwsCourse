package rjm.romek.awscourse.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rjm.romek.awscourse.SpringContext;
import rjm.romek.awscourse.model.Chapter;
import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.model.User;
import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.repository.TaskRepository;
import rjm.romek.awscourse.repository.UserTaskRepository;
import rjm.romek.awscourse.validator.TaskValidator;

@Service
public class UserTaskService {

    private UserTaskRepository userTaskRepository;
    private TaskRepository taskRepository;

    @Autowired
    public UserTaskService(UserTaskRepository userTaskRepository, TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.userTaskRepository = userTaskRepository;
    }

    public List<UserTask> getOrCreate(User user, Chapter chapter) {
        List<Task> byChapter = taskRepository.findByChapter(chapter);
        List<UserTask> userTasks = userTaskRepository.findAllByUserAndTask_Chapter(user, chapter);

        for (Task task : byChapter) {
            if (userTasks.stream().noneMatch(ut -> ut.getTask().getTaskId() == task.getTaskId())) {
                userTaskRepository.save(new UserTask(user, task));
            }
        }

        return userTaskRepository.findAllByUserAndTask_Chapter(user, chapter);
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
