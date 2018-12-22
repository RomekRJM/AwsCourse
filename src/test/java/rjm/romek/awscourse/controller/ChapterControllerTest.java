package rjm.romek.awscourse.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Map;
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

import com.amazonaws.AmazonServiceException;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import rjm.romek.awscourse.model.Chapter;
import rjm.romek.awscourse.model.User;
import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.repository.ChapterRepository;
import rjm.romek.awscourse.repository.TaskRepository;
import rjm.romek.awscourse.service.UserTaskService;
import rjm.romek.awscourse.verifier.VerifierTestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ChapterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChapterRepository chapterRepository;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private UserTaskService userTaskService;

    private final Chapter CHAPTER = new Chapter(1l, "Test");
    private final Map<String, String> ANSWERS = ImmutableMap.of("key", "value");
    private final UserTask USER_TASK = VerifierTestUtils.createUserTask("test", ANSWERS);

    @Before
    public void setUp() {
        when(chapterRepository.findById(anyLong())).thenReturn(Optional.of(CHAPTER));
        when(taskRepository.findByChapter(CHAPTER)).thenReturn(Collections.emptyList());
        when(userTaskService.getOrCreate(any(User.class), any(Chapter.class)))
                .thenReturn(
                        ImmutableList.of(
                                USER_TASK
                        )
                );
        when(userTaskService.checkTaskAndSaveAnswer(eq(USER_TASK), any(Map.class))).thenReturn(true);
    }

    @Test
    @WithUserDetails("tester")
    public void shouldGetChapterAndTasks() throws Exception {
        mockMvc.perform(get("/" + ChapterController.PATH))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("Chapter Test")));
    }

    @Test
    @WithUserDetails("tester")
    public void shouldPostChapterAndTasks() throws Exception {
        mockMvc.perform(
                post("/" + ChapterController.PATH)
                        .param("id", CHAPTER.getChapterId().toString())
                        .param("answer", "blah-blah-0987")
                        .with(csrf())
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("Chapter Test")));
    }

    @Test
    @WithUserDetails("tester")
    public void shouldReportErrorsOnPost() throws Exception {
        final String exceptionMessage = "Error talking to service.";
        AmazonServiceException exc = new AmazonServiceException(exceptionMessage);
        when(userTaskService.checkTaskAndSaveAnswer(eq(USER_TASK), any(Map.class))).thenThrow(exc);

        mockMvc.perform(
                post("/" + ChapterController.PATH)
                        .param("id", CHAPTER.getChapterId().toString())
                        .param("answer", "blah-blah-0987")
                        .with(csrf())
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString(exceptionMessage)));
    }

}