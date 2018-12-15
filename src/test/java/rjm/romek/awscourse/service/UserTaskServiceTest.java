package rjm.romek.awscourse.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.repository.TaskRepository;
import rjm.romek.awscourse.repository.UserTaskRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTaskServiceTest {

    @Mock
    private Task task;

    @Mock
    private UserTask userTask;

    @Mock
    private UserTaskRepository userTaskRepository;

    @Mock
    private TaskRepository taskRepository;

    private UserTaskService taskService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        doReturn(task).when(userTask).getTask();
        doReturn(DummyTaskVerifier.class).when(task).getVerifier();
        taskService = new UserTaskService(userTaskRepository, taskRepository);
    }

    @Test
    public void checkTaskShouldReturnValidatorIsCompleteResult() throws Exception {
        when(userTask.getDone()).thenReturn(Boolean.FALSE);
        assertTrue(taskService.checkTaskAndSaveAnswer(userTask, Collections.emptyMap()));
    }

    @Test(expected = RuntimeException.class)
    public void checkTaskShouldThrowRuntimeException() throws Exception {
        doReturn(FailingTaskVerifier.class).when(task).getVerifier();
        assertTrue(taskService.checkTaskAndSaveAnswer(userTask, Collections.emptyMap()));
    }

}