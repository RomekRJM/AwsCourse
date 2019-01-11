package rjm.romek.awscourse.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.ec2.AmazonEC2;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AwsAssumedServiceTest {

    @Autowired
    private AwsAssumedService awsAssumedService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateAmazonEC2() {
        AmazonEC2 amazonEC2 = awsAssumedService.amazonEC2(new BasicSessionCredentials("a", "s", "s"), "eu-west-1");
        assertNotNull(amazonEC2);
    }
}