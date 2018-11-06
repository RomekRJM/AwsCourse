package rjm.romek.awscourse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rjm.romek.awscourse.model.Chapter;
import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.repository.ChapterRepository;
import rjm.romek.awscourse.repository.TaskRepository;

@Controller
public class S3ChapterControler {

    public static final String PATH = "hello";
    public static final String CHAPTER = "chapter";
    public static final String TASKS = "tasks";
    public static final String CHAPTER_ID = "chapterId";

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/")
    public String chapter(@RequestParam(name=CHAPTER_ID, required=false, defaultValue="1") Long chapterId, Model model) {
        Chapter chapter = chapterRepository.findById(chapterId).get();
        List<Task> tasks = taskRepository.findByChapter(chapter);

        model.addAttribute(CHAPTER, chapter);
        model.addAttribute(TASKS, tasks);
        return PATH;
    }

}
