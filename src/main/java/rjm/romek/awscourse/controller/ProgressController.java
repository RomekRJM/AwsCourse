package rjm.romek.awscourse.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import rjm.romek.awscourse.model.Chapter;
import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.model.User;
import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.repository.ChapterRepository;
import rjm.romek.awscourse.repository.TaskRepository;
import rjm.romek.awscourse.repository.UserRepository;
import rjm.romek.awscourse.repository.UserTaskRepository;

@Controller
public class ProgressController {
    public static final String PATH = "progress";
    public static final String PROGRESS = "progress";

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private UserTaskRepository userTaskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("progress")
    public ModelAndView showProgress() {
        return new ModelAndView(PATH, prepareModelMap());
    }

    private Map<String, Object> prepareModelMap() {
        Map<String, Object> modelMap = new ModelMap();

        List<Chapter> chapters = chapterRepository.findAllByOrderByChapterId();
        List<User> users = userRepository.findAllByOrderByUsername();
        List<ChapterProgress> progress = new ArrayList<>();

        for (Chapter chapter : chapters) {
            List<Task> tasks = taskRepository.findByChapter(chapter);
            List<UserTask> userTasks = userTaskRepository.findAllByTask_Chapter(chapter);
            progress.add(new ChapterProgress(chapter, users, tasks, userTasks));
        }

        modelMap.put(PROGRESS, progress);
        return modelMap;
    }

    public class ChapterProgress {

        private final String chapterTitle;
        private final String[] userNames;
        private final String[] taskDescriptions;
        private final Boolean[][] taskProgress;

        public ChapterProgress(Chapter chapter, List<User> users, List<Task> tasks, List<UserTask> userTasks) {
            chapterTitle = chapter.getTitle();
            userNames = new String[users.size()];
            taskDescriptions = new String[tasks.size()];
            taskProgress = new Boolean[users.size()][tasks.size()];
            int userCounter = 0;
            int taskCounter = 0;

            for (User user : users) {
                userNames[userCounter] = user.getUsername();
                for (Task task : tasks) {

                    if (userCounter == 0) {
                        taskDescriptions[taskCounter] = StringUtils.truncate(task.getDescription(), 32);
                    }

                    Optional<UserTask> found = userTasks.stream()
                            .filter(x -> (x.getTask().getTaskId() == task.getTaskId()
                                    && x.getUser().getUserId() == user.getUserId()))
                            .findFirst();

                    taskProgress[userCounter][taskCounter] = found.isPresent() && found.get().getDone();
                    ++taskCounter;
                }
                taskCounter = 0;
                ++userCounter;
            }
        }

        public String getChapterTitle() {
            return chapterTitle;
        }

        public String[] getUserNames() {
            return userNames;
        }

        public String[] getTaskDescriptions() {
            return taskDescriptions;
        }

        public Boolean[][] getTaskProgress() {
            return taskProgress;
        }
    }
}
