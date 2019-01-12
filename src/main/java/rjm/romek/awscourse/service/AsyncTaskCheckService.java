package rjm.romek.awscourse.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.Getter;
import rjm.romek.awscourse.controller.ChapterController;
import rjm.romek.awscourse.model.User;
import rjm.romek.awscourse.model.UserTask;

@Service
public class AsyncTaskCheckService {

    @Autowired
    @Qualifier("fixedThreadPool")
    private ExecutorService executorService;

    @Autowired
    private UserTaskService userTaskService;

    Logger logger = LoggerFactory.getLogger(ChapterController.class);

    public TaskCheckResult checkTasks(User user, List<UserTask> tasks, Map<String, String> answers) {
        List<Future<Boolean>> futures = new ArrayList<>();
        Map<UserTask, Throwable> exceptions = new HashMap<>();
        int done = 0;

        for(UserTask task : tasks)
        {
            futures.add(executorService.submit(new CheckTaskThread(answers, task)));
        };

        for(int i=0; i<futures.size(); ++i) {
            Future<Boolean> future = futures.get(i);
            UserTask task = tasks.get(i);
            Throwable exception = null;

            try {
                if (future.get(8, TimeUnit.SECONDS)) {
                    ++done;
                }
                exception = null;
            } catch (InterruptedException e) {
                exception = e;
            } catch (ExecutionException e) {
                exception = ExceptionUtils.getRootCause(e);
            } catch (TimeoutException e) {
                exception = new RuntimeException("Couldn't get any response from the server.");
            } finally {
                if(exception != null) {
                    logger.debug(String.format("%s encountered error on %s",
                            user.getUsername(), task.getTask().getDescription()),
                            exception.getMessage());
                    exception.printStackTrace();
                    exceptions.put(task, exception);
                }
            }
        }

        return new TaskCheckResult(exceptions, done);
    }

    private class CheckTaskThread implements Callable<Boolean> {

        private final Map<String, String> answers;
        private final UserTask task;

        public CheckTaskThread(Map<String, String> answers, UserTask task) {
            this.answers = answers;
            this.task = task;
        }

        @Override
        public Boolean call() throws Exception {
            return userTaskService.checkTaskAndSaveAnswer(task, answers);
        }
    }

    public class TaskCheckResult{
        @Getter
        private Map<UserTask, Throwable> exceptions;

        @Getter
        private Integer numberOfDone;

        public TaskCheckResult(Map<UserTask, Throwable> exceptions, Integer numberOfDone) {
            this.exceptions = exceptions;
            this.numberOfDone = numberOfDone;
        }
    }
}
