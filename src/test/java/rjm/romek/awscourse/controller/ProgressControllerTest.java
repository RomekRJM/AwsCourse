package rjm.romek.awscourse.controller;

import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProgressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("tester")
    public void testShowProgress() throws Exception {
        mockMvc.perform(get("/" + ProgressController.PATH))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ProgressController.PROGRESS, instanceOf(ArrayList.class)))
                .andDo(print());
    }
}