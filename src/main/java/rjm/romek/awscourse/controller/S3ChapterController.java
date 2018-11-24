package rjm.romek.awscourse.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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
import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.repository.ChapterRepository;
import rjm.romek.awscourse.repository.TaskRepository;

@Controller
public class S3ChapterController {

    public static final String PATH = "chapter";
    public static final String NEXT = "next";
    public static final String CHAPTER = "chapter";
    public static final String TASKS = "tasks";
    public static final String CHAPTER_ID = "chapterId";
    public static final String ANSWER = "answer";

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping({"/", "/chapter/{id}"})
    public ModelAndView showForm(@PathVariable Optional<Long> id) {
        Long chapterId = id.orElse(1l);
        return new ModelAndView(PATH, loadChapterAndTasks(chapterId, ""));
    }

    @PostMapping({"/", "/chapter"})
    public String chapter(HttpServletRequest request,
                          @RequestParam(value="id", required=true) Long id,
                          @RequestParam(value="answer", required=true) String answer,
                          Model model) {
        model.addAllAttributes(loadChapterAndTasks(id, answer));
        return PATH;
    }

    private Map<String, Object> loadChapterAndTasks(Long chapterId, String answer) {
        Map<String, Object> modelMap = new ModelMap();

        Chapter chapter = chapterRepository.findById(chapterId).get();
        List<Task> tasks = taskRepository.findByChapter(chapter);
        tasks.get(0).setAnswer(answer);

        modelMap.put(CHAPTER, chapter);
        modelMap.put(TASKS, tasks);

        return modelMap;
    }
}
