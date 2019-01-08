package rjm.romek.awscourse.verifier.db;

import static org.junit.Assert.*;

import java.util.Map;

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
public class DBConnectionVerifierTest {

    private final Map<String, String> ANSWERS_PASSING = ImmutableMap.of(
            "user", "", "password", "", "endpoint", "~", "database", "test"
        );
    private final UserTask USER_TASK_PASSING = TestUtils.createUserTask("test", ANSWERS_PASSING);

    private final Map<String, String> ANSWERS_FAILING = ImmutableMap.of(
            "user", "", "password", "", "endpoint", "nonexisting", "database", "blah"
    );
    private final UserTask USER_TASK_FAILING = TestUtils.createUserTask("test", ANSWERS_FAILING);

    @Autowired
    private DBConnectionVerifier dbConnectionVerifier;

    @Test
    public void isCompletedShouldReturnTrue() {
        assertTrue(dbConnectionVerifier.isCompleted(USER_TASK_PASSING));
    }

    @Test(expected = RuntimeException.class)
    public void isCompletedShouldThrowException() {
        assertTrue(dbConnectionVerifier.isCompleted(USER_TASK_FAILING));
    }

}