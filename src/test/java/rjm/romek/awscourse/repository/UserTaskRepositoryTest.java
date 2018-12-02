package rjm.romek.awscourse.repository;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

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

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserTaskRepositoryTest {

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserTaskRepository userTaskRepository;

    private final Chapter chapter = new Chapter();

    private final User user = new User("user");

    private final Task task1 = new Task(chapter);

    private final Task task2 = new Task(chapter);

    private final UserTask userTask11 = new UserTask(user, task1);
    private final UserTask userTask12 = new UserTask(user, task2);

    @Before
    public void setUp() {
        chapterRepository.save(chapter);
        userRepository.save(user);
        taskRepository.save(task1);
        taskRepository.save(task2);
        userTaskRepository.save(userTask11);
        userTaskRepository.save(userTask12);
    }

    @Test
    public void findByUserAndTask() throws Exception {
        assertNotNull(userTaskRepository.findByUserAndTask(user, task1));
    }

    @Test
    public void findAllByUserAndTask_Chapter() throws Exception {
        assertEquals(userTaskRepository.findAllByUserAndTask_Chapter(user, chapter).size(), 2);
    }

}