package rjm.romek.awscourse.repository;

import static junit.framework.TestCase.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import rjm.romek.awscourse.model.Chapter;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ChapterRepositoryTest {

    @Autowired
    private ChapterRepository chapterRepository;

    @Test
    public void testSaveAndDelete() {
        Chapter chapter = chapterRepository.save(new Chapter("title"));
        assertTrue(chapterRepository.findById(chapter.getChapterId()).isPresent());
    }

}