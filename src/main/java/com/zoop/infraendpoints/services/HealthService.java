package com.zoop.infraendpoints.services;

import com.zoop.infraendpoints.domains.health.Dependency;
import com.zoop.infraendpoints.domains.health.Health;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HealthService {

    private final Health health;

    private HealthService(List<Dependency> dependencies) {
        this.health = new Health(dependencies);
    }

    public Health check() {
        return health.check();
    }

    public static class Builder {

        private final List<Dependency> dependencies;

        private Builder() {
            this.dependencies = new ArrayList<>();
        }

        public static Builder newInstance() {
            return new Builder();
        }

        public Builder addDependency(Dependency dependency) {
            this.dependencies.add(dependency);
            return this;
        }

        public HealthService build() {
            return new HealthService(this.dependencies);
        }

    }

}
