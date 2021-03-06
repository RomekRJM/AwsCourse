package rjm.romek.awscourse.verifier.ec2;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsRequest;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsResult;
import com.amazonaws.services.ec2.model.GroupIdentifier;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.SecurityGroup;
import com.amazonaws.services.ec2.model.Tag;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.testutils.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AllEC2VerifiersTest {

    private static final String TASK_DESCRIPTION = "(instanceId)(*vpcId=vpc-2032f54b)(*instanceType=m3.medium)" +
            "(*subnetId=subnet-c08152a7)(*ami=ami-e6fc5e91)(*tags=app:awscourse)(*ingress=tcp:80:80:10.0.0.0/8)";
    private static final String INSTANCE_ID = "i-a123456789";

    private static final UserTask USER_TASK = TestUtils.createUserTask(
            TASK_DESCRIPTION, ImmutableMap.of("instanceId", INSTANCE_ID)
    );

    @MockBean
    private AmazonEC2 amazonEC2;

    @Mock
    private DescribeInstancesResult describeInstancesResult;

    @Mock
    private DescribeSecurityGroupsResult describeSecurityGroupsResult;

    @Mock
    private Instance instance;

    private List<Instance> instances;

    @Mock
    private Reservation reservation;

    private List<Reservation> reservations;

    @Mock
    private GroupIdentifier groupIdentifier;

    private List<GroupIdentifier> groupIdentifiers;

    @Mock
    private SecurityGroup securityGroup;

    private List<SecurityGroup> securityGroups;

    private final IpPermission matchAllPermission = TestUtils.createIpPermission("-1", 0, 65535, "0.0.0.0/0");
    private final IpPermission matchHttpPermission = TestUtils.createIpPermission("tcp", 80, 80, "0.0.0.0/0");
    private final IpPermission noMatchUdpPermission = TestUtils.createIpPermission("udp", 80, 80, "0.0.0.0/0");

    private List<IpPermission> ipPermissions;

    @Autowired
    private EC2TypeVerifier ec2TypeVerifier;

    @Autowired
    private EC2AmiVerifier ec2AmiVerifier;

    @Autowired
    private EC2SubnetVerifier ec2SubnetVerifier;

    @Autowired
    private EC2TagsVerifier ec2TagsVerifier;

    @Autowired
    private EC2VpcVerifier ec2VpcVerifier;

    @Autowired
    private EC2SecurityGroupVerifier ec2SecurityGroupVerifier;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        instances = ImmutableList.of(instance);
        reservations = ImmutableList.of(reservation);
        groupIdentifiers = ImmutableList.of(groupIdentifier);
        securityGroups = ImmutableList.of(securityGroup);
        ipPermissions = new ArrayList<>();

        when(amazonEC2.describeInstances(any(DescribeInstancesRequest.class)))
                .thenReturn(describeInstancesResult);
        when(describeInstancesResult.getReservations()).thenReturn(reservations);
        when(reservation.getInstances()).thenReturn(instances);
        when(amazonEC2.describeSecurityGroups(any(DescribeSecurityGroupsRequest.class)))
                .thenReturn(describeSecurityGroupsResult);
        when(describeSecurityGroupsResult.getSecurityGroups()).thenReturn(securityGroups);
        when(instance.getSecurityGroups()).thenReturn(groupIdentifiers);
        when(securityGroup.getIpPermissions()).thenReturn(ipPermissions);
    }

    @Test
    public void isCompletedShouldReturnTrueOnCorrectType() {
        when(instance.getInstanceType()).thenReturn("m3.medium");

        assertTrue(ec2TypeVerifier.isCompleted(USER_TASK));
    }

    @Test
    public void isCompletedShouldReturnFalseOnWrongType() {
        when(instance.getInstanceType()).thenReturn("t2.nano");

        assertFalse(ec2TypeVerifier.isCompleted(USER_TASK));
    }

    @Test
    public void isCompletedShouldReturnTrueOnCorrectVpc() {
        when(instance.getVpcId()).thenReturn("vpc-2032f54b");

        assertTrue(ec2VpcVerifier.isCompleted(USER_TASK));
    }

    @Test
    public void isCompletedShouldReturnFalseOnWrongVpc() {
        when(instance.getVpcId()).thenReturn("vpc-00000000");

        assertFalse(ec2VpcVerifier.isCompleted(USER_TASK));
    }

    @Test
    public void isCompletedShouldReturnTrueOnCorrectAmi() {
        when(instance.getImageId()).thenReturn("ami-e6fc5e91");

        assertTrue(ec2AmiVerifier.isCompleted(USER_TASK));
    }

    @Test
    public void isCompletedShouldReturnFalseOnWrongAmi() {
        when(instance.getImageId()).thenReturn("ami-00000000");

        assertFalse(ec2AmiVerifier.isCompleted(USER_TASK));
    }

    @Test
    public void isCompletedShouldReturnTrueOnCorrectSubnet() {
        when(instance.getSubnetId()).thenReturn("subnet-c08152a7");

        assertTrue(ec2SubnetVerifier.isCompleted(USER_TASK));
    }

    @Test
    public void isCompletedShouldReturnFalseOnWrongSubnet() {
        when(instance.getSubnetId()).thenReturn("subnet-00000000");

        assertFalse(ec2SubnetVerifier.isCompleted(USER_TASK));
    }

    @Test
    public void isCompletedShouldReturnTrueOnCorrectTags() {
        when(instance.getTags()).thenReturn(ImmutableList.of(new Tag("app", "awscourse")));

        assertTrue(ec2TagsVerifier.isCompleted(USER_TASK));
    }

    @Test
    public void isCompletedShouldReturnFalseOnWrongTags() {
        when(instance.getTags()).thenReturn(ImmutableList.of(new Tag("wrong", "tag")));

        assertFalse(ec2TagsVerifier.isCompleted(USER_TASK));
    }

    @Test
    public void isCompletedShouldReturnTrueOnSecurityGroupMatchHttp() {
        ipPermissions.add(matchHttpPermission);

        assertTrue(ec2SecurityGroupVerifier.isCompleted(USER_TASK));
    }

    @Test
    public void isCompletedShouldReturnTrueOnSecurityGroupMatchAll() {
        ipPermissions.add(matchAllPermission);

        assertTrue(ec2SecurityGroupVerifier.isCompleted(USER_TASK));
    }

    @Test
    public void isCompletedShouldReturnFalseOnSecurityGroupWrongProtocol() {
        ipPermissions.add(noMatchUdpPermission);

        assertFalse(ec2SecurityGroupVerifier.isCompleted(USER_TASK));
    }
}