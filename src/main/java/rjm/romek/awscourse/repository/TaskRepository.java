package rjm.romek.awscourse.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rjm.romek.awscourse.model.Chapter;
import rjm.romek.awscourse.model.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByChapter(Chapter chapter);
}
