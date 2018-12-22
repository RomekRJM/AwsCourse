package rjm.romek.awscourse.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import rjm.romek.awscourse.model.Chapter;
import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.model.User;
import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.repository.ChapterRepository;
import rjm.romek.awscourse.repository.TaskRepository;
import rjm.romek.awscourse.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserTaskServiceTestIT {

    @Autowired
    private UserTaskService userTaskService;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    private final Chapter chapter = new Chapter();
    private final User user = new User("user");
    private final Task task = new Task(chapter);

    @Before
    public void setUp() {
        chapterRepository.save(chapter);
        userRepository.save(user);
        taskRepository.save(task);
    }

    @Test
    public void testGetOrCreate() {
        List<UserTask> userTasks = userTaskService.getOrCreate(user, chapter);

        assertEquals(1, userTasks.size());
        UserTask userTask = userTasks.get(0);
        assertEquals(task, userTask.getTask());
        assertEquals(user, userTask.getUser());
    }
}