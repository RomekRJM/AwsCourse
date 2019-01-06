package rjm.romek.awscourse.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.model.DBInstance;
import com.amazonaws.services.rds.model.DescribeDBInstancesRequest;
import com.amazonaws.services.rds.model.DescribeDBInstancesResult;

@Service
public class RDSService implements InstanceService<DBInstance> {

    private final AmazonRDS amazonRDS;

    @Autowired
    public RDSService(AmazonRDS amazonRDS) {
        this.amazonRDS = amazonRDS;
    }

    public Optional<DBInstance> getInstance(String dBInstanceIdentifier) {
        DescribeDBInstancesResult result = amazonRDS.describeDBInstances(
                new DescribeDBInstancesRequest().withDBInstanceIdentifier(dBInstanceIdentifier));

        return result.getDBInstances().stream().findFirst();
    }
}
