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

@Service
public class EC2Service {

    private static final String UNAUTHORIZED = "UnauthorizedOperation";
    private static final Integer VOLUME_SIZE = Integer.valueOf(100);

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

    public boolean dryRunCreateVolume(String az) {
        DryRunResult<CreateVolumeRequest> createVolumeRequestDryRunResult = amazonEC2.dryRun(
                new CreateVolumeRequest(VOLUME_SIZE, az));

        if (createVolumeRequestDryRunResult.isSuccessful()) {
            return true;
        } else if (UNAUTHORIZED.equals(createVolumeRequestDryRunResult.getDryRunResponse().getErrorCode())) {
            return false;
        }

        throw createVolumeRequestDryRunResult.getDryRunResponse();
    }
}
