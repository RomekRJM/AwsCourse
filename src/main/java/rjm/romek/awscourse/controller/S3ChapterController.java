package rjm.romek.awscourse.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import rjm.romek.awscourse.model.Chapter;
import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.repository.ChapterRepository;
import rjm.romek.awscourse.repository.TaskRepository;
import rjm.romek.awscourse.repository.UserTaskRepository;
import rjm.romek.awscourse.service.UserTaskService;
import rjm.romek.awscourse.session.SessionInfo;

@Controller
public class S3ChapterController {

    public static final String PATH = "chapter";
    public static final String NEXT = "next";
    public static final String CHAPTER = "chapter";
    public static final String TASKS = "tasks";
    public static final String ANSWER = "answer";
    public static final String ID_PARAM = "id";
    public static final String CSRF_PARAM = "_csrf";

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserTaskRepository userTaskRepository;

    @Autowired
    private UserTaskService taskService;

    @Resource(name = "sessionInfo")
    private SessionInfo sessionInfo;

    @GetMapping({"/", "/chapter", "/chapter/{id}"})
    public ModelAndView showForm(@PathVariable Optional<Long> id) {
        Long chapterId = id.orElse(1000l);
        return new ModelAndView(PATH, prepareModelMap(chapterId));
    }

    @PostMapping({"/", "/chapter"})
    public String chapter(@RequestParam(value="id", required=true) Long id,
                          @RequestParam Map<String,String> allRequestParams,
                          Model model) {

        Map<String, Object> modelMap = prepareModelMap(id);
        List<UserTask> tasks = (List<UserTask>)modelMap.get(TASKS);

        final Map<String, String> answers = removeUselessEntries(allRequestParams);
        tasks.forEach(t -> taskService.checkTaskAndSaveAnswer(t, answers));

        model.addAllAttributes(modelMap);
        return PATH;
    }

    private Map<String, Object> prepareModelMap(Long chapterId) {
        Map<String, Object> modelMap = new ModelMap();

        Chapter chapter = chapterRepository.findById(chapterId).get();
        List<UserTask> tasks = userTaskRepository.findAllByUserAndTask_Chapter(sessionInfo.getCurrentUser(), chapter);

        modelMap.put(CHAPTER, chapter);
        modelMap.put(TASKS, tasks);

        return modelMap;
    }

    private Map<String, String> removeUselessEntries(Map<String, String> map) {
        map.remove(ID_PARAM);
        map.remove(CSRF_PARAM);
        return map;
    }
}
