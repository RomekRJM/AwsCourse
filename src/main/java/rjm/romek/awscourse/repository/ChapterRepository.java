package rjm.romek.awscourse.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rjm.romek.awscourse.model.Chapter;

@Repository
public interface ChapterRepository extends CrudRepository<Chapter, Long> {
    List<Chapter> findAllByOrderByChapterId();
}
