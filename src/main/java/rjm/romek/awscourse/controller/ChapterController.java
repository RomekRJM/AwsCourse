package rjm.romek.awscourse.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import rjm.romek.awscourse.model.Chapter;
import rjm.romek.awscourse.model.User;
import rjm.romek.awscourse.model.UserPrincipal;
import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.repository.ChapterRepository;
import rjm.romek.awscourse.service.AsyncTaskCheckService;
import rjm.romek.awscourse.service.AsyncTaskCheckService.TaskCheckResult;
import rjm.romek.awscourse.service.UserTaskService;

@Controller
public class ChapterController {

    Logger logger = LoggerFactory.getLogger(ChapterController.class);

    public static final String PATH = "chapter";
    public static final String PATH_404 = "/error/404.html";
    public static final String REDIRECT404 = "redirect:" + PATH_404;
    public static final String PATH_CONGRATS = "congrats";
    public static final String NEXT_CHAPTER = "nextChapter";
    public static final String NEXT_CHAPTER_PREFIX = "/chapter/";
    public static final String ALL_DONE = "allDone";
    public static final String CHAPTER = "chapter";
    public static final String TASKS = "tasks";
    public static final String ERROR = "error";
    public static final String ID_PARAM = "id";
    public static final String CSRF_PARAM = "_csrf";

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private UserTaskService userTaskService;

    @Autowired
    private AsyncTaskCheckService asyncTaskCheckService;

    @GetMapping("/congrats")
    public ModelAndView showCongrats() {
        return new ModelAndView(PATH_CONGRATS);
    }

    @GetMapping({"/", "/chapter", "/chapter/{id}"})
    public ModelAndView showForm(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                 @PathVariable Optional<Long> id) {
        Long chapterId = id.orElse(1000l);
        Optional<Chapter> chapter = chapterRepository.findById(chapterId);

        if (chapter.isPresent()) {
            return new ModelAndView(PATH, prepareModelMap(userPrincipal.getUser(), chapter.get()));
        } else {
            logger.info("User " + userPrincipal.getUsername() + " picked wrong chapter.");
            return new ModelAndView(REDIRECT404);
        }
    }

    @PostMapping({"/", "/chapter"})
    public String chapter(@AuthenticationPrincipal UserPrincipal userPrincipal,
                          @RequestParam(value = "id", required = true) Long chapterId,
                          @RequestParam Map<String, String> allRequestParams,
                          Model model) {

        User user = userPrincipal.getUser();
        Optional<Chapter> chapter = chapterRepository.findById(chapterId);
        Map<String, Object> modelMap = prepareModelMap(user, chapter.get());
        List<UserTask> tasks = (List<UserTask>) modelMap.get(TASKS);
        Map<String, String> answers = removeUselessEntries(allRequestParams);

        TaskCheckResult result = asyncTaskCheckService.checkTasks(user, tasks, answers);
        addExceptionMessages(modelMap, result);

        Boolean allTasksDone = result.getNumberOfDone() == tasks.size();
        Long nextChapterId = chapterId + 1;
        prepareForNextChapter(modelMap, allTasksDone, nextChapterId);
        model.addAllAttributes(modelMap);

        return PATH;
    }

    private void prepareForNextChapter(Map<String, Object> modelMap, Boolean allTasksDone, Long nextChapterId) {
        String nextChapter = shouldGoToCongrats(allTasksDone, nextChapterId) ?
                PATH_CONGRATS : (NEXT_CHAPTER_PREFIX + nextChapterId);

        modelMap.put(NEXT_CHAPTER, nextChapter);
        modelMap.put(ALL_DONE, allTasksDone);
    }

    private Map<String, Object> prepareModelMap(User user, Chapter chapter) {
        Map<String, Object> modelMap = new ModelMap();
        List<UserTask> tasks = userTaskService.getOrCreate(user, chapter);

        modelMap.put(CHAPTER, chapter);
        modelMap.put(TASKS, tasks);

        return modelMap;
    }

    private Map<String, String> removeUselessEntries(Map<String, String> map) {
        map.remove(ID_PARAM);
        map.remove(CSRF_PARAM);
        return map;
    }

    private void addExceptionMessages(Map<String, Object> modelMap, TaskCheckResult result) {
        for(UserTask task : result.getExceptions().keySet()) {
            Throwable exception = result.getExceptions().get(task);
            modelMap.put(ERROR + task.getTask().getTaskId(), exception.getMessage());
        }
    }

    private boolean shouldGoToCongrats(Boolean done, Long nextChapterId) {
        return done && !chapterRepository.findById(nextChapterId).isPresent();
    }
}
