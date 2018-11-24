package rjm.romek.awscourse.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.repository.TaskRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {

    @Mock
    private Task task;

    @Mock
    private TaskRepository taskRepository;

    private TaskService taskService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        doReturn(DummyTaskValidator.class).when(task).getValidator();
        taskService = new TaskService(taskRepository);
    }

    @Test
    public void checkTaskShouldReturnTrueOnDoneTask() throws Exception {
        when(task.getDone()).thenReturn(Boolean.TRUE);
        assertTrue(taskService.checkTaskAndSaveAnswer(task, ""));
    }

    @Test
    public void checkTaskShouldReturnValidatorIsCompleteResult() throws Exception {
        when(task.getDone()).thenReturn(Boolean.FALSE);
        assertTrue(taskService.checkTaskAndSaveAnswer(task, ""));
    }

    @Test(expected = RuntimeException.class)
    public void checkTaskShouldThrowRuntimeException() throws Exception {
        doReturn(FailingTaskValidator.class).when(task).getValidator();
        assertTrue(taskService.checkTaskAndSaveAnswer(task, ""));
    }

}