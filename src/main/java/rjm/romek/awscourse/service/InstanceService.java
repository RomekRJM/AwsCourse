package rjm.romek.awscourse.service;

import java.util.Optional;

public interface InstanceService<T> {
    Optional<T> getInstance(String instanceId);
}
