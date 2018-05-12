package com.zoop.infraendpoints.services;

import com.zoop.infraendpoints.domains.health.Dependency;
import com.zoop.infraendpoints.domains.health.Health;

import java.util.List;
import java.util.Objects;

public class HealthService {

    private final Health health;

    public HealthService(List<Dependency> dependencies) {
        if (Objects.isNull(dependencies)) {
            throw new IllegalArgumentException("Dependencies list could not be null");
        }

        this.health = new Health(dependencies);
    }

    public Health check() {
        return health.check();
    }

}
