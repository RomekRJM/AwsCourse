package rjm.romek.awscourse.verifier.iam;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import java.util.LinkedHashMap;
import java.util.Map;

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

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.CreateVolumeRequest;
import com.amazonaws.services.ec2.model.DryRunResult;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.model.AWSSecurityTokenServiceException;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.amazonaws.services.securitytoken.model.Credentials;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.service.AwsAssumedService;
import rjm.romek.awscourse.service.EC2Service;
import rjm.romek.awscourse.testutils.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AllCreateVolumeVerifiersTest {

    private static final String TASK_DESCRIPTION = "Create role, that can be assumed by anyone and allows " +
            "ec2:DescribeInstances on * and paste role arn in here: (roleArn).(region=eu-west-1)(deniedTypes=standard)";
    private static final String ROLE_ARN = "arn:aws:iam::0123456789:role/AwsCourseDescribeInstances";
    private static final String REGION = "eu-west-1";
    private static final String AZ = "eu-west-1a";
    private static final CreateVolumeRequest V_100_GP2 = new CreateVolumeRequest(100, AZ).withVolumeType("gp2");
    private static final CreateVolumeRequest V_101_GP2 = new CreateVolumeRequest(101, AZ).withVolumeType("gp2");
    private static final CreateVolumeRequest V_100_STANDARD = new CreateVolumeRequest(100, AZ).withVolumeType("standard");
    private static final CreateVolumeRequest V_101_STANDARD = new CreateVolumeRequest(101, AZ).withVolumeType("standard");
    private static final CreateVolumeRequest V_100_IO1 = new CreateVolumeRequest(100, AZ).withVolumeType("io1")
            .withIops(EC2Service.DEFAULT_IOPS);
    private static final CreateVolumeRequest V_101_IO1 = new CreateVolumeRequest(101, AZ).withVolumeType("io1")
            .withIops(EC2Service.DEFAULT_IOPS);
    private static final CreateVolumeRequest V_100_SC1 = new CreateVolumeRequest(100, AZ).withVolumeType("sc1");
    private static final CreateVolumeRequest V_101_SC1 = new CreateVolumeRequest(101, AZ).withVolumeType("sc1");
    private static final CreateVolumeRequest V_100_ST1 = new CreateVolumeRequest(100, AZ).withVolumeType("st1");
    private static final CreateVolumeRequest V_101_ST1 = new CreateVolumeRequest(101, AZ).withVolumeType("st1");

    private static final AmazonServiceException UNAUTHORIZED = new AmazonServiceException("");

    private static final DryRunResult<CreateVolumeRequest> SUCCESFULL =
            new DryRunResult<>(true, null, "", null);

    private static final DryRunResult<CreateVolumeRequest> UNSUCCESFULL =
            new DryRunResult<>(false, null, "", UNAUTHORIZED);

    private final Credentials credentials = new Credentials("access", "secret", "session", null);

    @Mock
    private Map<String, String> answers;

    private UserTask userTask;

    @Mock
    private AssumeRoleResult assumeRoleResult;

    @Mock
    private AmazonEC2 amazonEC2;

    @Mock
    private DryRunResult<CreateVolumeRequest> result;

    @MockBean
    private AWSSecurityTokenService stsClient;

    @MockBean
    private AwsAssumedService awsAssumedService;

    @Autowired
    private CreateVolumeInRegionAllowedVerifier createVolumeInRegionAllowedVerifier;

    @Autowired
    private CreateVolumeInRegionDisallowedVerifier createVolumeInRegionDisallowedVerifier;

    @Autowired
    private CreateVolumeUpToSizeAllowedVerifier createVolumeUpToSizeAllowedVerifier;

    @Autowired
    private CreateVolumeUpToSizeWithTypeAllowedVerifier createVolumeUpToSizeWithTypeAllowedVerifier;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(assumeRoleResult.getCredentials()).thenReturn(credentials);
        when(amazonEC2.dryRun(any(CreateVolumeRequest.class))).thenReturn(result);
        when(awsAssumedService.amazonEC2(any(BasicSessionCredentials.class), anyString())).thenReturn(amazonEC2);
        when(stsClient.assumeRole(any(AssumeRoleRequest.class))).thenReturn(assumeRoleResult);

        answers = new LinkedHashMap<>();
        answers.put("roleArn", ROLE_ARN);
        answers.put("region", REGION);
        userTask = TestUtils.createUserTask(
                TASK_DESCRIPTION, answers
        );

        UNAUTHORIZED.setErrorCode("UnauthorizedOperation");
    }

    @Test
    public void isCompletedShouldReturnTrueForAllowedVerifier() {
        when(result.isSuccessful()).thenReturn(true);
        ArgumentCaptor<BasicSessionCredentials> credentialsArgumentCaptor = ArgumentCaptor.forClass(BasicSessionCredentials.class);

        assertTrue(createVolumeInRegionAllowedVerifier.isCompleted(userTask));
        verify(awsAssumedService, times(1)).amazonEC2(credentialsArgumentCaptor.capture(), any());
        assertEquals(credentialsArgumentCaptor.getValue().getSessionToken(), "session");
    }

    @Test(expected = AWSSecurityTokenServiceException.class)
    public void isCompletedShouldThrowExceptionForAllowedVerifier() {
        when(stsClient.assumeRole(any(AssumeRoleRequest.class))).thenThrow(
                new AWSSecurityTokenServiceException("Access denied")
        );

        createVolumeInRegionAllowedVerifier.isCompleted(userTask);
    }

    @Test
    public void isCompletedShouldReturnTrueForDisallowedVerifier() {
        when(result.isSuccessful()).thenReturn(false);
        when(result.getDryRunResponse()).thenReturn(UNAUTHORIZED);

        assertTrue(createVolumeInRegionDisallowedVerifier.isCompleted(userTask));
    }

    @Test
    public void isCompletedShouldReturnFalseForDisallowedVerifier() {
        when(result.isSuccessful()).thenReturn(true);
        ArgumentCaptor<BasicSessionCredentials> credentialsArgumentCaptor = ArgumentCaptor.forClass(BasicSessionCredentials.class);

        assertFalse(createVolumeInRegionDisallowedVerifier.isCompleted(userTask));
        verify(awsAssumedService, times(1)).amazonEC2(credentialsArgumentCaptor.capture(), anyString());
        assertEquals(credentialsArgumentCaptor.getValue().getSessionToken(), "session");
    }

    @Test(expected = AmazonServiceException.class)
    public void isCompletedShouldThrowExceptionForDisallowedVerifier() {
        AmazonServiceException amazonServiceException = new AmazonServiceException("");
        amazonServiceException.setErrorCode("UnhandledException");
        when(result.isSuccessful()).thenReturn(false);
        when(result.getDryRunResponse()).thenReturn(amazonServiceException);

        createVolumeInRegionDisallowedVerifier.isCompleted(userTask);
    }

    @Test
    public void isCompletedShouldReturnTrueForVolumeUpToSizeVerifier() {
        when(amazonEC2.dryRun(eq(V_100_GP2))).thenReturn(SUCCESFULL);
        when(amazonEC2.dryRun(eq(V_101_GP2))).thenReturn(UNSUCCESFULL);

        assertTrue(createVolumeUpToSizeAllowedVerifier.isCompleted(userTask));
    }

    @Test
    public void isCompletedShouldReturnFalseForVolumeUpToSizeVerifier() {
        when(amazonEC2.dryRun(eq(V_100_GP2))).thenReturn(UNSUCCESFULL);
        when(amazonEC2.dryRun(eq(V_101_GP2))).thenReturn(UNSUCCESFULL);

        assertFalse(createVolumeUpToSizeAllowedVerifier.isCompleted(userTask));
    }

    @Test
    public void isCompletedShouldReturnTrueForVolumeUpToSizeWithTypeVerifier() {
        when(amazonEC2.dryRun(eq(V_100_IO1))).thenReturn(SUCCESFULL);
        when(amazonEC2.dryRun(eq(V_100_GP2))).thenReturn(SUCCESFULL);
        when(amazonEC2.dryRun(eq(V_100_SC1))).thenReturn(SUCCESFULL);
        when(amazonEC2.dryRun(eq(V_100_ST1))).thenReturn(SUCCESFULL);
        when(amazonEC2.dryRun(eq(V_100_STANDARD))).thenReturn(SUCCESFULL);
        when(amazonEC2.dryRun(eq(V_101_IO1))).thenReturn(SUCCESFULL);
        when(amazonEC2.dryRun(eq(V_101_GP2))).thenReturn(SUCCESFULL);
        when(amazonEC2.dryRun(eq(V_101_SC1))).thenReturn(SUCCESFULL);
        when(amazonEC2.dryRun(eq(V_101_ST1))).thenReturn(SUCCESFULL);
        when(amazonEC2.dryRun(eq(V_101_STANDARD))).thenReturn(UNSUCCESFULL);

        assertTrue(createVolumeUpToSizeWithTypeAllowedVerifier.isCompleted(userTask));
    }

    @Test
    public void isCompletedShouldReturnFalseForVolumeUpToSizeWithTypeVerifierWithWrongSize() {
        when(amazonEC2.dryRun(eq(V_100_IO1))).thenReturn(UNSUCCESFULL);
        when(amazonEC2.dryRun(eq(V_100_GP2))).thenReturn(UNSUCCESFULL);
        when(amazonEC2.dryRun(eq(V_100_SC1))).thenReturn(UNSUCCESFULL);
        when(amazonEC2.dryRun(eq(V_100_ST1))).thenReturn(UNSUCCESFULL);
        when(amazonEC2.dryRun(eq(V_100_STANDARD))).thenReturn(UNSUCCESFULL);
        when(amazonEC2.dryRun(eq(V_101_IO1))).thenReturn(UNSUCCESFULL);
        when(amazonEC2.dryRun(eq(V_101_GP2))).thenReturn(UNSUCCESFULL);
        when(amazonEC2.dryRun(eq(V_101_SC1))).thenReturn(UNSUCCESFULL);
        when(amazonEC2.dryRun(eq(V_101_ST1))).thenReturn(UNSUCCESFULL);
        when(amazonEC2.dryRun(eq(V_101_STANDARD))).thenReturn(UNSUCCESFULL);

        assertFalse(createVolumeUpToSizeWithTypeAllowedVerifier.isCompleted(userTask));
    }

    @Test
    public void isCompletedShouldReturnFalseForVolumeUpToSizeWithTypeVerifierWithWrongType() {
        when(amazonEC2.dryRun(eq(V_100_IO1))).thenReturn(UNSUCCESFULL);
        when(amazonEC2.dryRun(eq(V_100_GP2))).thenReturn(UNSUCCESFULL);
        when(amazonEC2.dryRun(eq(V_100_SC1))).thenReturn(UNSUCCESFULL);
        when(amazonEC2.dryRun(eq(V_100_ST1))).thenReturn(UNSUCCESFULL);
        when(amazonEC2.dryRun(eq(V_100_STANDARD))).thenReturn(SUCCESFULL);
        when(amazonEC2.dryRun(eq(V_101_IO1))).thenReturn(UNSUCCESFULL);
        when(amazonEC2.dryRun(eq(V_101_GP2))).thenReturn(UNSUCCESFULL);
        when(amazonEC2.dryRun(eq(V_101_SC1))).thenReturn(UNSUCCESFULL);
        when(amazonEC2.dryRun(eq(V_101_ST1))).thenReturn(UNSUCCESFULL);
        when(amazonEC2.dryRun(eq(V_101_STANDARD))).thenReturn(SUCCESFULL);

        assertFalse(createVolumeUpToSizeWithTypeAllowedVerifier.isCompleted(userTask));
    }

}