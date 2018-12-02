package rjm.romek.awscourse.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import rjm.romek.awscourse.model.Chapter;
import rjm.romek.awscourse.repository.ChapterRepository;
import rjm.romek.awscourse.repository.TaskRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class S3ChapterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChapterRepository chapterRepository;

    @MockBean
    private TaskRepository taskRepository;

    private final Chapter testChapter = new Chapter(1l, "Test");

    @Before
    public void setUp() {
        when(chapterRepository.findById(anyLong())).thenReturn(Optional.of(testChapter));
        when(taskRepository.findByChapter(testChapter)).thenReturn(Collections.emptyList());
    }

    @Test
    @WithUserDetails("tester")
    public void shouldGetChapterAndTasks() throws Exception {
        mockMvc.perform(get("/" + S3ChapterController.PATH))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("Chapter Test")));
    }

    @Test
    @WithUserDetails("tester")
    public void shouldPostChapterAndTasks() throws Exception {
        mockMvc.perform(
                post("/" + S3ChapterController.PATH)
                        .param("id", testChapter.getChapterId().toString())
                        .param("answer", "blah-blah-0987")
                        .with(csrf())
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("Chapter Test")));
    }

}