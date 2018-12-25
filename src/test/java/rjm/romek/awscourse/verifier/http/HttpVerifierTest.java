package rjm.romek.awscourse.verifier.http;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.ImmutableMap;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.testutils.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class HttpVerifierTest {

    private static final UserTask USER_TASK_WITH_VALID_URL = TestUtils.createUserTask(
            "(u)", ImmutableMap.of("u", "google.com")
    );

    private static final UserTask USER_TASK_WITH_INVALID_URL = TestUtils.createUserTask(
            "(u)", ImmutableMap.of("u", "sadijosjdiojnkjncvuipsjzmohsadbjsa.io")
    );

    private static final UserTask USER_TASK_WITH_BLANK_URL = TestUtils.createUserTask(
            "(u)", ImmutableMap.of("u", "")
    );

    @Autowired
    private HttpVerifier httpVerifier;

    @Test
    public void isCompletedShouldReturnTrueOnValidUrl() {
        assertTrue(httpVerifier.isCompleted(USER_TASK_WITH_VALID_URL));
    }

    @Test(expected = RuntimeException.class)
    public void isCompletedShouldThrowExceptionOnInvalidUrl() {
        httpVerifier.isCompleted(USER_TASK_WITH_INVALID_URL);
    }

    @Test
    public void isCompletedShouldReturnTrueOnBlankUrl() {
        assertFalse(httpVerifier.isCompleted(USER_TASK_WITH_BLANK_URL));
    }
}