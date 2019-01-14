package rjm.romek.awscourse.verifier.elasticbeanstalk;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.amazonaws.services.elasticbeanstalk.AWSElasticBeanstalk;
import com.amazonaws.services.elasticbeanstalk.model.DescribeEnvironmentHealthRequest;
import com.amazonaws.services.elasticbeanstalk.model.DescribeEnvironmentHealthResult;
import com.google.common.collect.ImmutableMap;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.testutils.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EnvironmentHealthyVerifierTest {

    private static final String ENV_NAME = "test";

    private static final UserTask USER_TASK = TestUtils.createUserTask(
            "(environmentName)", ImmutableMap.of("environmentName", ENV_NAME)
    );

    @MockBean
    private AWSElasticBeanstalk amazonElasticBeanstalk;

    @Mock
    private DescribeEnvironmentHealthResult describeEnvironmentHealthResult;

    @Autowired
    private EnvironmentHealthyVerifier environmentHealthyVerifier;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(amazonElasticBeanstalk.describeEnvironmentHealth(any(DescribeEnvironmentHealthRequest.class)))
                .thenReturn(describeEnvironmentHealthResult);
    }

    @Test
    public void isCompletedShouldReturnTrue() {
        when(describeEnvironmentHealthResult.getColor()).thenReturn("Green");

        assertTrue(environmentHealthyVerifier.isCompleted(USER_TASK));
    }

    @Test
    public void isCompletedShouldReturnFalse() {
        when(describeEnvironmentHealthResult.getColor()).thenReturn("Red");

        assertFalse(environmentHealthyVerifier.isCompleted(USER_TASK));
    }
}