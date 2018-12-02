package rjm.romek.awscourse.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import rjm.romek.awscourse.model.Chapter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChapterRepositoryTest {

    @Autowired
    private ChapterRepository chapterRepository;

    @Before
    public void setUp() {
        chapterRepository.deleteAll();
    }

    @Test
    public void testSaveAndDelete() {
        chapterRepository.save(new Chapter(1l, "title"));
        assertEquals(1, chapterRepository.count());

        chapterRepository.deleteAll();
        assertEquals(0, chapterRepository.count());
    }

}