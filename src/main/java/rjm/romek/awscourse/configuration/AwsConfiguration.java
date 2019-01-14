package rjm.romek.awscourse.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.elasticbeanstalk.AWSElasticBeanstalk;
import com.amazonaws.services.elasticbeanstalk.AWSElasticBeanstalkClientBuilder;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.AmazonRDSClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;

@Profile({"prod", "dev"})
@Configuration
public class AwsConfiguration {

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard().build();
    }

    @Bean
    public AmazonEC2 amazonEC2() {
        return AmazonEC2ClientBuilder.standard().build();
    }

    @Bean
    public AWSElasticBeanstalk amazonElasticBeanstalk() {
        return AWSElasticBeanstalkClientBuilder.standard().build();
    }

    @Bean
    public AWSSecurityTokenService amazonSTS() {
        return AWSSecurityTokenServiceClientBuilder.standard().build();
    }

    @Bean
    public AmazonRDS amazonRDS() {
        return AmazonRDSClientBuilder.standard().build();
    }

}
