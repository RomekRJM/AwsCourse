package rjm.romek.awscourse;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.elasticbeanstalk.AWSElasticBeanstalk;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;

@Profile("test")
@Configuration
public class MockAwsConfiguration {

    @Bean
    @Primary
    public AmazonS3 amazonS3() {
        return Mockito.mock(AmazonS3.class);
    }

    @Bean
    @Primary
    public AmazonEC2 amazonEC2() {
        return Mockito.mock(AmazonEC2.class);
    }

    @Bean
    @Primary
    public AWSElasticBeanstalk amazonElasticBeanstalk() {
        return Mockito.mock(AWSElasticBeanstalk.class);
    }

    @Bean
    @Primary
    public AWSSecurityTokenService amazonSTS() {
        return Mockito.mock(AWSSecurityTokenService.class);
    }

    @Bean
    @Primary
    public AmazonRDS amazonRDS() {
        return Mockito.mock(AmazonRDS.class);
    }
}
