package rjm.romek.awscourse.verifier.s3;

import static org.junit.Assert.assertTrue;
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
import com.google.common.collect.ImmutableMap;

import rjm.romek.awscourse.model.UserTask;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class KeyExistsVerifierTest {
    @MockBean
    private AmazonS3 amazonS3;

    @Mock
    private UserTask userTask;

    @Autowired
    private KeyExistsVerifier keyExistsVerifier;

    private static final String EXISTING_BUCKET = "existing-bucket";
    private static final String EXISTING_KEY = "existing-key";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(amazonS3.doesObjectExist(EXISTING_BUCKET, EXISTING_KEY)).thenReturn(true);
    }

    @Test
    public void isCompletedShouldReturnTrue() {
        when(userTask.getAnswers())
                .thenReturn(ImmutableMap.of(
                        "bucketName", EXISTING_BUCKET,
                        "keyName", EXISTING_KEY)
                );

        assertTrue(keyExistsVerifier.isCompleted(userTask));
    }
}