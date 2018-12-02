package rjm.romek.awscourse.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import rjm.romek.awscourse.model.Chapter;
import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.validator.BucketExistsValidator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Before
    public void setUp() {
        taskRepository.deleteAll();
        chapterRepository.deleteAll();
    }

    @Test
    public void testSaveAndDelete() {
        Chapter chapter = new Chapter("Chapter");
        chapterRepository.save(chapter);

        Task task = new Task(chapter, "Task", "Description", BucketExistsValidator.class);

        taskRepository.save(task);
        assertEquals(1, taskRepository.count());
    }

    @Test
    public void testFindByChapter() {
        Chapter chapter = new Chapter("Chapter");
        chapterRepository.save(chapter);

        Task task1 = new Task(chapter, "Task1", "Description", BucketExistsValidator.class);
        Task task2 = new Task(chapter, "Task2", "Description", BucketExistsValidator.class);

        taskRepository.save(task1);
        taskRepository.save(task2);

        List<Task> tasks = taskRepository.findByChapter(chapter);
        assertTrue(tasks.stream().anyMatch(x -> x.getTaskId() == task1.getTaskId()));
        assertTrue(tasks.stream().anyMatch(x -> x.getTaskId() == task1.getTaskId()));
    }

}