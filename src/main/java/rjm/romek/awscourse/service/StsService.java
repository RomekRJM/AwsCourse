package rjm.romek.awscourse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.amazonaws.services.securitytoken.model.Credentials;

@Service
public class StsService {

    @Autowired
    private AWSSecurityTokenService stsClient;

    public BasicSessionCredentials assumeRole(String roleArn) {
        AssumeRoleResult assumeRoleResult = stsClient.assumeRole(
                new AssumeRoleRequest()
                        .withRoleArn(roleArn)
                        .withRoleSessionName("awscourse" + System.currentTimeMillis())
        );
        Credentials credentials = assumeRoleResult.getCredentials();

        return new BasicSessionCredentials(
                credentials.getAccessKeyId(), credentials.getSecretAccessKey(), credentials.getSessionToken()
        );
    }
}
