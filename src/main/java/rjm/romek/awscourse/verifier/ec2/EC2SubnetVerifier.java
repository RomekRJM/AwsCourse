package rjm.romek.awscourse.verifier.ec2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.ec2.model.Instance;

import rjm.romek.awscourse.service.EC2Service;

@Service
public class EC2SubnetVerifier extends EC2Verifier {
    private static final String ATTRIBUTE_NAME = "subnetId";

    @Autowired
    public EC2SubnetVerifier(EC2Service ec2Service) {
        super(ec2Service, ATTRIBUTE_NAME);
    }

    @Override
    protected String getEC2AttributeValue(Instance instance) {
        return instance.getSubnetId();
    }
}
