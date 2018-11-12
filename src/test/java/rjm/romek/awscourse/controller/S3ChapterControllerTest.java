package rjm.romek.awscourse.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import rjm.romek.awscourse.model.Chapter;
import rjm.romek.awscourse.repository.ChapterRepository;
import rjm.romek.awscourse.repository.TaskRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(S3ChapterController.class)
@AutoConfigureMockMvc
public class S3ChapterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChapterRepository chapterRepository;

    @MockBean
    private TaskRepository taskRepository;

    @Test
    @WithUserDetails("tester")
    public void shouldGetChapterAndTasks() throws Exception {
        Chapter testChapter = new Chapter("Test");
        when(chapterRepository.findById(anyLong())).thenReturn(Optional.of(testChapter));
        when(taskRepository.findByChapter(testChapter)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/" + S3ChapterController.PATH))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("Chapter Test")));
    }

}