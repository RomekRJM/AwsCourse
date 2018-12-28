package rjm.romek.awscourse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.elasticbeanstalk.AWSElasticBeanstalk;
import com.amazonaws.services.elasticbeanstalk.model.DescribeEnvironmentHealthRequest;
import com.amazonaws.services.elasticbeanstalk.model.DescribeEnvironmentHealthResult;

@Service
public class ElasticBeanstalkService {

    private static final String OK = "Ok";

    private final AWSElasticBeanstalk amazonElasticBeanstalk;

    @Autowired
    public ElasticBeanstalkService(AWSElasticBeanstalk amazonElasticBeanstalk) {
        this.amazonElasticBeanstalk = amazonElasticBeanstalk;
    }

    public boolean environmentHealthy(String environmentName) {
        DescribeEnvironmentHealthResult describeEnvironmentHealthResult =
                amazonElasticBeanstalk.describeEnvironmentHealth(
                        new DescribeEnvironmentHealthRequest().withEnvironmentName(environmentName)
                );

        return OK.equals(describeEnvironmentHealthResult.getHealthStatus());
    }
}
