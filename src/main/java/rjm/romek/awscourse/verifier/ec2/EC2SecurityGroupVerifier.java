package rjm.romek.awscourse.verifier.ec2;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.ec2.model.GroupIdentifier;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.SecurityGroup;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.service.EC2Service;

@Service
public class EC2SecurityGroupVerifier extends EC2Verifier {
    private static final String ATTRIBUTE_NAME = "ingress";
    private static final String RANGE_ALL = "0.0.0.0/0";
    private static final String ANY_PROTOCOL = "-1";
    private static final String SPLIT_CHAR = ":";

    @Autowired
    public EC2SecurityGroupVerifier(EC2Service ec2Service) {
        super(ec2Service, ATTRIBUTE_NAME);
    }

    @Override
    protected List<GroupIdentifier> getInstanceAttributeValue(Instance instance) {
        return instance.getSecurityGroups();
    }

    protected boolean done(UserTask userTask, Instance instance) {
        String attributeValue = getAttributeValue(userTask.getTask());
        String[] attributeValueSplitted = StringUtils.split(attributeValue, SPLIT_CHAR);
        String protocol = attributeValueSplitted[0];
        Integer fromPort = Integer.valueOf(attributeValueSplitted[1]);
        Integer toPort = Integer.valueOf(attributeValueSplitted[2]);
        String ip4Range = attributeValueSplitted[3];

        List<GroupIdentifier> ec2AttributeValue = getInstanceAttributeValue(instance);
        String[] securityGroupIds = ec2AttributeValue.stream().map(GroupIdentifier::getGroupId).toArray(String[]::new);
        List<SecurityGroup> securityGroups = ((EC2Service)service).getSecurityGroups(securityGroupIds);

        return allowsTrafficIn(securityGroups, protocol, fromPort, toPort, ip4Range);
    }

    private boolean allowsTrafficIn(List<SecurityGroup> securityGroups,
                                    String protocol, Integer fromPort, Integer toPort, String ip4Range) {
        for (SecurityGroup securityGroup : securityGroups) {
            List<IpPermission> ipPermissions = securityGroup.getIpPermissions();
            for (IpPermission ipPermission : ipPermissions) {
                int rulesToMatch = 3;
                if (StringUtils.equals(ipPermission.getIpProtocol(), ANY_PROTOCOL)) {
                    rulesToMatch -= 2;
                }
                if (StringUtils.equals(ipPermission.getIpProtocol(), protocol)) {
                    --rulesToMatch;
                }
                if (ipPermission.getFromPort() <= fromPort && ipPermission.getToPort() >= toPort) {
                    --rulesToMatch;
                }
                if (ipPermission.getIpv4Ranges().stream().anyMatch(
                        x -> (StringUtils.equals(x.getCidrIp(), RANGE_ALL)) || StringUtils.equals(x.getCidrIp(), ip4Range))
                ) {
                    --rulesToMatch;
                }
                if (rulesToMatch <= 0) {
                    return true;
                }
            }
        }

        return false;
    }
}
