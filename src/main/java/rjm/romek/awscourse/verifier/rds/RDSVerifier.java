package rjm.romek.awscourse.verifier.rds;

import com.amazonaws.services.rds.model.DBInstance;

import rjm.romek.awscourse.service.RDSService;
import rjm.romek.awscourse.verifier.InstanceVerifier;

public abstract class RDSVerifier extends InstanceVerifier<DBInstance> {

    public static final String DB_INSTANCE_ID = "dbInstanceId";

    public RDSVerifier(RDSService rdsService, String attributeName) {
        super(rdsService, DB_INSTANCE_ID, attributeName);
    }
}
