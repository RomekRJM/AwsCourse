package rjm.romek.awscourse.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rjm.romek.awscourse.model.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

}
