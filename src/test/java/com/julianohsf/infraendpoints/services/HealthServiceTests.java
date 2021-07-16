package com.julianohsf.infraendpoints.services;

import com.julianohsf.infraendpoints.domains.health.Dependency;
import com.julianohsf.infraendpoints.domains.health.Health;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class HealthServiceTests {

    @Test
    public void checkSuccess() {
        HealthService service = buildService(new Dependency("Success", Boolean.TRUE, () -> Boolean.TRUE));

        Health health = service.check();
        assertEquals("UP", health.getStatus());
    }

    private HealthService buildService(Dependency... dependencies) {
        HealthService.Builder builder = HealthService.Builder.newInstance();

        for (Dependency dependency: dependencies) {
            builder.addDependency(dependency);
        }

        return builder.build();
    }

}