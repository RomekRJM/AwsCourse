package rjm.romek.awscourse.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import rjm.romek.awscourse.model.Task;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Mock
    private Task task;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        doReturn(DummyTaskValidator.class).when(task).getValidator();
    }

    @Test
    public void checkTaskShouldReturnTrueOnDoneTask() throws Exception {
        when(task.getDone()).thenReturn(Boolean.TRUE);
        assertTrue(taskService.checkTask(task));
    }

    @Test
    public void checkTaskShouldReturnValidatorIsCompleteResult() throws Exception {
        when(task.getDone()).thenReturn(Boolean.FALSE);
        assertTrue(taskService.checkTask(task));
    }

    @Test(expected = RuntimeException.class)
    public void checkTaskShouldThrowRuntimeException() throws Exception {
        doReturn(FailingTaskValidator.class).when(task).getValidator();
        assertTrue(taskService.checkTask(task));
    }

}