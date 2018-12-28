package rjm.romek.awscourse.verifier.iam;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.model.AWSSecurityTokenServiceException;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.google.common.collect.ImmutableMap;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.service.AwsAssumedService;
import rjm.romek.awscourse.testutils.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DescribeInstancesVerifierTest {

    private static final String TASK_DESCRIPTION = "Create role, that can be assumed by anyone and allows " +
            "ec2:DescribeInstances on * and paste role arn in here: (roleArn).";
    private static final String ROLE_ARN = "arn:aws:iam::0123456789:role/AwsCourseDescribeInstances";

    private static final UserTask USER_TASK = TestUtils.createUserTask(
            TASK_DESCRIPTION, ImmutableMap.of("roleArn", ROLE_ARN)
    );

    private final Credentials credentials = new Credentials("access", "secret", "session", null);

    @Mock
    private AssumeRoleResult assumeRoleResult;

    @Mock
    private AmazonEC2 amazonEC2;

    @MockBean
    private AWSSecurityTokenService stsClient;

    @MockBean
    private AwsAssumedService awsAssumedService;

    @Autowired
    private DescribeInstancesVerifier describeInstancesVerifier;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(assumeRoleResult.getCredentials()).thenReturn(credentials);
        when(awsAssumedService.amazonEC2(any(BasicSessionCredentials.class))).thenReturn(amazonEC2);
        when(stsClient.assumeRole(any(AssumeRoleRequest.class))).thenReturn(assumeRoleResult);
    }

    @Test
    public void isCompletedShouldReturnTrue() {
        ArgumentCaptor<BasicSessionCredentials> credentialsArgumentCaptor = ArgumentCaptor.forClass(BasicSessionCredentials.class);

        assertTrue(describeInstancesVerifier.isCompleted(USER_TASK));
        verify(amazonEC2, times(1)).describeInstances();
        verify(awsAssumedService, times(1)).amazonEC2(credentialsArgumentCaptor.capture());
        assertEquals(credentialsArgumentCaptor.getValue().getSessionToken(), "session");
    }

    @Test(expected = AWSSecurityTokenServiceException.class)
    public void isCompletedShouldThrowException() {
        when(stsClient.assumeRole(any(AssumeRoleRequest.class))).thenThrow(
                new AWSSecurityTokenServiceException("Access denied")
        );

        assertTrue(describeInstancesVerifier.isCompleted(USER_TASK));
    }
}