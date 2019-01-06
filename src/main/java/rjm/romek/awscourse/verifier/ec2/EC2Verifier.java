package rjm.romek.awscourse.verifier.ec2;

import com.amazonaws.services.ec2.model.Instance;

import rjm.romek.awscourse.service.EC2Service;
import rjm.romek.awscourse.verifier.InstanceVerifier;

public abstract class EC2Verifier extends InstanceVerifier<Instance> {
    public static final String INSTANCE_ID = "instanceId";

    public EC2Verifier(EC2Service ec2Service, String attributeName) {
        super(ec2Service, INSTANCE_ID, attributeName);
    }

}
