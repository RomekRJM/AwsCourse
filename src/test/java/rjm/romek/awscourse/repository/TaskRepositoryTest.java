package rjm.romek.awscourse.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import rjm.romek.awscourse.model.Chapter;
import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.validator.BucketExistsValidator;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @After
    public void tearDown() {
        taskRepository.deleteAll();
        chapterRepository.deleteAll();
    }

    @Test
    public void testSaveAndDelete() {
        Chapter chapter = new Chapter("Chapter");
        chapterRepository.save(chapter);

        Task task = new Task(chapter, "Task", "Description", Boolean.FALSE, BucketExistsValidator.class);

        taskRepository.save(task);
        assertEquals(1, taskRepository.count());
    }

    @Test
    public void testFindByChapter() {
        Chapter chapter = new Chapter("Chapter");
        chapterRepository.save(chapter);

        Task task1 = new Task(chapter, "Task1", "Description", Boolean.FALSE, BucketExistsValidator.class);
        Task task2 = new Task(chapter, "Task2", "Description", Boolean.FALSE, BucketExistsValidator.class);

        taskRepository.save(task1);
        taskRepository.save(task2);

        List<Task> tasks = taskRepository.findByChapter(chapter);
        assertTrue(tasks.contains(task1));
        assertTrue(tasks.contains(task2));
    }

}