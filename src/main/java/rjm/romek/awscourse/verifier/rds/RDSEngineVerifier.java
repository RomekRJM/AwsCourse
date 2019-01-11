package rjm.romek.awscourse.verifier.rds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.rds.model.DBInstance;

import rjm.romek.awscourse.service.RDSService;

@Service
public class RDSEngineVerifier extends RDSVerifier {

    private static final String ATTRIBUTE_NAME = "engine";

    @Autowired
    public RDSEngineVerifier(RDSService rdsService) {
        super(rdsService, ATTRIBUTE_NAME);
    }

    @Override
    protected String getInstanceAttributeValue(DBInstance instance) {
        return instance.getEngine();
    }
}
