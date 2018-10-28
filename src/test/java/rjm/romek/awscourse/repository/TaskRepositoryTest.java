package rjm.romek.awscourse.repository;

import static org.junit.Assert.assertEquals;

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

    @Test
    public void testSave() {
        Chapter chapter = new Chapter("Chapter");
        chapterRepository.save(chapter);

        Task task = new Task(chapter, "Task", "Description", Boolean.FALSE, BucketExistsValidator.class);

        taskRepository.save(task);
        assertEquals(1, taskRepository.count());

        taskRepository.deleteAll();
        assertEquals(0, taskRepository.count());

        chapterRepository.deleteAll();
    }

}