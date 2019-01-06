package rjm.romek.awscourse.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.CreateVolumeRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsRequest;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsResult;
import com.amazonaws.services.ec2.model.DryRunResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.SecurityGroup;
import com.amazonaws.services.ec2.model.VolumeType;

@Service
public class EC2Service implements InstanceService<Instance> {

    private static final String UNAUTHORIZED = "UnauthorizedOperation";

    private final AmazonEC2 amazonEC2;

    @Autowired
    public EC2Service(AmazonEC2 amazonEC2) {
        this.amazonEC2 = amazonEC2;
    }

    public Optional<Instance> getInstance(String instanceId) {
        DescribeInstancesResult result = amazonEC2.describeInstances(
                new DescribeInstancesRequest().withInstanceIds(instanceId));
        List<Reservation> reservations = result.getReservations();
        Set<Instance> instances = new HashSet<Instance>();

        for (Reservation reservation : reservations) {
            instances.addAll(reservation.getInstances());
        }

        return instances.stream().findFirst();
    }

    public List<SecurityGroup> getSecurityGroups(String... securityGroupIds) {
        DescribeSecurityGroupsResult result = amazonEC2.describeSecurityGroups(
                new DescribeSecurityGroupsRequest().withGroupIds(securityGroupIds));

        return result.getSecurityGroups();
    }

    public boolean dryRunCreateVolume(String az, Integer volumeSize, String volumeType) {
        DryRunResult<CreateVolumeRequest> createVolumeRequestDryRunResult = amazonEC2.dryRun(
                createVolumeRequest(az, volumeSize, volumeType)
        );

        if (createVolumeRequestDryRunResult.isSuccessful()) {
            return true;
        } else if (UNAUTHORIZED.equals(createVolumeRequestDryRunResult.getDryRunResponse().getErrorCode())) {
            return false;
        }

        throw createVolumeRequestDryRunResult.getDryRunResponse();
    }

    private CreateVolumeRequest createVolumeRequest(String az, Integer volumeSize, String volumeType) {
        CreateVolumeRequest request = new CreateVolumeRequest(volumeSize, az).withVolumeType(volumeType);

        if(VolumeType.fromValue(volumeType) == VolumeType.Io1) {
            request.withIops(500);
        }

        return request;
    }
}
