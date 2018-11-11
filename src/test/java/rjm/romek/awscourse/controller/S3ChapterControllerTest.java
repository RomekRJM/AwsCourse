package rjm.romek.awscourse.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import rjm.romek.awscourse.model.Chapter;
import rjm.romek.awscourse.repository.ChapterRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure=false)
public class S3ChapterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ChapterRepository chapterRepository;

    @Test
    public void shouldGetChapterAndTasks() throws Exception {
        chapterRepository.save(new Chapter("Test"));

        mockMvc.perform(get(S3ChapterController.PATH))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("Chapter Test")));
    }

}