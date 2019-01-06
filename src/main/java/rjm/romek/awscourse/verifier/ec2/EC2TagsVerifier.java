package rjm.romek.awscourse.verifier.ec2;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Tag;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.service.EC2Service;

@Service
public class EC2TagsVerifier extends EC2Verifier {
    private static final String ATTRIBUTE_NAME = "tags";
    private static final String SPLIT_CHAR = ":";

    @Autowired
    public EC2TagsVerifier(EC2Service ec2Service) {
        super(ec2Service, ATTRIBUTE_NAME);
    }

    @Override
    protected List<Tag> getInstanceAttributeValue(Instance instance) {
        return instance.getTags();
    }

    protected boolean done(UserTask userTask, Instance instance) {
        List<Tag> tags = getInstanceAttributeValue(instance);
        String attributeValue = getAttributeValue(userTask.getTask());
        String [] keyValue = StringUtils.split(attributeValue, SPLIT_CHAR);

        return tags.stream().anyMatch(
                x -> StringUtils.equals(x.getKey(), keyValue[0]) && StringUtils.equals(x.getValue(), keyValue[1])
        );
    }
}
