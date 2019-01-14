package rjm.romek.awscourse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.elasticbeanstalk.AWSElasticBeanstalk;
import com.amazonaws.services.elasticbeanstalk.model.DescribeEnvironmentHealthRequest;
import com.amazonaws.services.elasticbeanstalk.model.DescribeEnvironmentHealthResult;

@Service
public class ElasticBeanstalkService {

    private static final String ALL = "All";
    private static final String GREEN = "Green";

    private final AWSElasticBeanstalk amazonElasticBeanstalk;

    @Autowired
    public ElasticBeanstalkService(AWSElasticBeanstalk amazonElasticBeanstalk) {
        this.amazonElasticBeanstalk = amazonElasticBeanstalk;
    }

    public boolean environmentHealthy(String environmentName) {
        DescribeEnvironmentHealthResult describeEnvironmentHealthResult =
                amazonElasticBeanstalk.describeEnvironmentHealth(
                        new DescribeEnvironmentHealthRequest()
                                .withEnvironmentName(environmentName)
                                .withAttributeNames(ALL)
                );

        return GREEN.equals(describeEnvironmentHealthResult.getColor());
    }
}
