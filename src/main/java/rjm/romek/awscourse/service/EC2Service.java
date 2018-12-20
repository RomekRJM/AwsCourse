package rjm.romek.awscourse.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeInstanceAttributeRequest;

@Service
public class EC2Service {

    @Autowired
    private AmazonEC2 amazonEC2;

    public static final String INSTANCE_TYPE = "instanceType";

    public boolean ec2InstanceUpAndIsOfType(String instanceId, String instanceType) {
        DescribeInstanceAttributeRequest request = new DescribeInstanceAttributeRequest()
                .withAttribute(INSTANCE_TYPE).withInstanceId(instanceId);
        String actualInstanceType = amazonEC2.describeInstanceAttribute(request).getInstanceAttribute().getInstanceType();

        return StringUtils.equals(instanceType, actualInstanceType);
    }
}
