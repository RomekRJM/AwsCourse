package rjm.romek.awscourse.verifier.ec2;

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

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeInstanceAttributeRequest;
import com.amazonaws.services.ec2.model.DescribeInstanceAttributeResult;
import com.amazonaws.services.ec2.model.InstanceAttribute;
import com.google.common.collect.ImmutableMap;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.testutils.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EC2ExistsVerifierTest {

    private static final String TASK_DESCRIPTION = "Create an EC2 instance of type t2.micro and paste id in here: " +
            "(instanceId).(*instanceType=m3.medium)";
    private static final String INSTANCE_ID = "i-a123456789";

    private static final UserTask USER_TASK = TestUtils.createUserTask(
            TASK_DESCRIPTION, ImmutableMap.of("instanceId", INSTANCE_ID)
    );

    @MockBean
    private AmazonEC2 amazonEC2;

    @Mock
    private DescribeInstanceAttributeResult describeInstanceAttributeResult;

    @Mock
    private InstanceAttribute instanceAttribute;

    @Autowired
    private EC2ExistsVerifier ec2ExistsVerifier;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(amazonEC2.describeInstanceAttribute(any(DescribeInstanceAttributeRequest.class)))
                .thenReturn(describeInstanceAttributeResult);
        when(describeInstanceAttributeResult.getInstanceAttribute()).thenReturn(instanceAttribute);
    }

    @Test
    public void isCompletedShouldReturnTrue() {
        when(instanceAttribute.getInstanceType()).thenReturn("m3.medium");

        assertTrue(ec2ExistsVerifier.isCompleted(USER_TASK));
    }

    @Test
    public void isCompletedShouldReturnFalseOnWrongInstanceType() {
        when(instanceAttribute.getInstanceType()).thenReturn("t2.nano");

        assertFalse(ec2ExistsVerifier.isCompleted(USER_TASK));
    }
}