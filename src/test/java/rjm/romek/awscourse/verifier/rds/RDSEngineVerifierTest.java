package rjm.romek.awscourse.verifier.rds;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.model.DBInstance;
import com.amazonaws.services.rds.model.DescribeDBInstancesResult;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.testutils.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RDSEngineVerifierTest {

    @Autowired
    private RDSEngineVerifier rdsEngineVerifier;

    private static final String TASK_DESCRIPTION = "Create an RDS instance using type 'db.t2.micro'. Paste instance " +
            "name in here: (dbInstanceId).(*engine=mysql)";
    private static final String INSTANCE_ID = "db-a123456789";

    private static final UserTask USER_TASK = TestUtils.createUserTask(
            TASK_DESCRIPTION, ImmutableMap.of("instanceId", INSTANCE_ID)
    );

    @MockBean
    private AmazonRDS amazonRDS;

    @Mock
    private DescribeDBInstancesResult describeDBInstancesResult;

    @Mock
    private DBInstance dbInstance;

    private List<DBInstance> dbInstances;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        dbInstances = ImmutableList.of(dbInstance);
        when(amazonRDS.describeDBInstances(any())).thenReturn(describeDBInstancesResult);
        when(describeDBInstancesResult.getDBInstances()).thenReturn(dbInstances);
    }

    @Test
    public void shouldReturnFalse() {
        when(dbInstance.getEngine()).thenReturn("postgres");
        assertFalse(rdsEngineVerifier.isCompleted(USER_TASK));
    }

    @Test
    public void shouldReturnTrue() {
        when(dbInstance.getEngine()).thenReturn("mysql");
        assertTrue(rdsEngineVerifier.isCompleted(USER_TASK));
    }
}