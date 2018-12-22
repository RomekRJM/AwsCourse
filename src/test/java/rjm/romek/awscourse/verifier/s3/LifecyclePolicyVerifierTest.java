package rjm.romek.awscourse.verifier.s3;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

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
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.StorageClass;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.model.UserTask;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LifecyclePolicyVerifierTest {

    @MockBean
    private AmazonS3 amazonS3;

    @Mock
    private BucketLifecycleConfiguration bucketLifecycleConfiguration;

    @Autowired
    private LifecyclePolicyVerifier lifecyclePolicyVerifier;

    private static final String EXISTING_BUCKET = "existing-bucket";
    private static final String TASK_DESCRIPTION = "Enable lifecycle policy for the bucket. " +
            "Use standard storage class for first 30 days,\n" +
            "then move it to standard infrequent and after that to glacier - 90 days after creation.\n" +
            "(*STANDARD_IA=30)(*GLACIER=90)";

    private static final List<BucketLifecycleConfiguration.Transition> MATCHING_TRANSITIONS =
            ImmutableList.of(new BucketLifecycleConfiguration.Transition()
                            .withStorageClass(StorageClass.StandardInfrequentAccess).withDays(30),
                    new BucketLifecycleConfiguration.Transition()
                            .withStorageClass(StorageClass.Glacier).withDays(90)
            );

    private static final List<BucketLifecycleConfiguration.Transition> DIFFERENT_TRANSITIONS =
            ImmutableList.of(new BucketLifecycleConfiguration.Transition()
                            .withStorageClass(StorageClass.OneZoneInfrequentAccess).withDays(7)
            );

    private static final List<BucketLifecycleConfiguration.Rule> MATCHING =
            ImmutableList.of(new BucketLifecycleConfiguration.Rule().withTransitions(
                    MATCHING_TRANSITIONS
            ));

    private static final List<BucketLifecycleConfiguration.Rule> DIFFERENT =
            ImmutableList.of(new BucketLifecycleConfiguration.Rule().withTransitions(
                    DIFFERENT_TRANSITIONS
            ));

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(amazonS3.getBucketLifecycleConfiguration(EXISTING_BUCKET)).thenReturn(bucketLifecycleConfiguration);
    }

    @Test
    public void isCompletedShouldReturnTrue() {
        when(bucketLifecycleConfiguration.getRules()).thenReturn(MATCHING);

        assertTrue(lifecyclePolicyVerifier.isCompleted(createUserTask()));
    }

    @Test
    public void isCompletedShouldReturnFalse() {
        when(bucketLifecycleConfiguration.getRules()).thenReturn(DIFFERENT);

        assertFalse(lifecyclePolicyVerifier.isCompleted(createUserTask()));
    }

    @Test
    public void isCompletedShouldReturnFalseOnNull() {
        when(amazonS3.getBucketLifecycleConfiguration(EXISTING_BUCKET)).thenReturn(null);

        assertFalse(lifecyclePolicyVerifier.isCompleted(createUserTask()));
    }

    private UserTask createUserTask() {
        Task task = new Task();
        task.setDescription(TASK_DESCRIPTION);
        UserTask userTask = new UserTask();
        userTask.setAnswers(ImmutableMap.of(
                "bucketName", EXISTING_BUCKET)
        );
        userTask.setTask(task);

        return userTask;
    }
}