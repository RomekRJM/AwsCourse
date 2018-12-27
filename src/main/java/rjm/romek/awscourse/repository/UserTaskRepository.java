package rjm.romek.awscourse.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rjm.romek.awscourse.model.Chapter;
import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.model.User;
import rjm.romek.awscourse.model.UserTask;

@Repository
public interface UserTaskRepository extends CrudRepository<UserTask, Long> {
    public UserTask findByUserAndTask(User user, Task task);
    public List<UserTask> findAllByUserAndTask_Chapter(User user, Chapter chapter);
    public List<UserTask> findAllByTask_Chapter(Chapter chapter);
}
