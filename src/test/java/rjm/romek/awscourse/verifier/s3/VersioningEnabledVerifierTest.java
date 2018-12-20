package rjm.romek.awscourse.verifier.s3;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.BucketVersioningConfiguration;
import com.google.common.collect.ImmutableMap;

import rjm.romek.awscourse.model.UserTask;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class VersioningEnabledVerifierTest {

    @MockBean
    private AmazonS3 amazonS3;

    @Mock
    private UserTask userTask;

    @Mock
    private BucketVersioningConfiguration bucketVersioningConfiguration;

    @Autowired
    private VersioningEnabledVerifier versioningEnabledVerifier;

    private static final String EXISTING_BUCKET = "existing-bucket";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(amazonS3.getBucketVersioningConfiguration(EXISTING_BUCKET)).thenReturn(bucketVersioningConfiguration);
    }

    @Test
    public void isCompletedShouldReturnTrue() {
        when(bucketVersioningConfiguration.getStatus()).thenReturn(BucketVersioningConfiguration.ENABLED);
        when(userTask.getAnswers())
                .thenReturn(ImmutableMap.of("bucketName", EXISTING_BUCKET)
                );

        assertTrue(versioningEnabledVerifier.isCompleted(userTask));
    }
}