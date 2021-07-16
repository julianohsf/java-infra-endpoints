package com.julianohsf.infraendpoints.domains.health;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class HealthTests {

    private Dependency success;
    private Dependency criticalFailure;
    private Dependency nonCriticalFailure;

    @Before
    public void setUp() {
        this.success = new Dependency("Success", Boolean.TRUE, () -> Boolean.TRUE);
        this.criticalFailure = new Dependency("Critical Failure", Boolean.TRUE, () -> Boolean.FALSE);
        this.nonCriticalFailure = new Dependency("Non Critical Failure", Boolean.FALSE, () -> Boolean.FALSE);
    }

    @Test
    public void testWithSuccess() {
        List<Dependency> dependencies = Arrays.asList(success);
        Health health = new Health(dependencies);
        health.check();
        assertEquals(Integer.valueOf(200), health.getHttpStatus());
        assertEquals(StatusEnum.UP.getStatus(), health.getStatus());
        assertEquals(StatusEnum.UP.getMessage(), health.getMessage());
        assertEquals(dependencies, health.getDependencies());

        String expectedJson = "{ \"status\": \"UP\", \"message\": \"All dependencies are up\", \"dependencies\": [ { \"name\": \"Success\", \"isCritical\": true, \"status\": \"UP\" } ] }";
        assertEquals(expectedJson, health.asJsonString());
    }

    @Test
    public void testWithMultipleSuccesses() {
        List<Dependency> dependencies = Arrays.asList(success, success);
        Health health = new Health(dependencies);
        health.check();
        assertEquals(Integer.valueOf(200), health.getHttpStatus());
        assertEquals(StatusEnum.UP.getStatus(), health.getStatus());
        assertEquals(StatusEnum.UP.getMessage(), health.getMessage());
        assertEquals(dependencies, health.getDependencies());

        String expectedJson = "{ \"status\": \"UP\", \"message\": \"All dependencies are up\", \"dependencies\": [ { \"name\": \"Success\", \"isCritical\": true, \"status\": \"UP\" }, { \"name\": \"Success\", \"isCritical\": true, \"status\": \"UP\" } ] }";
        assertEquals(expectedJson, health.asJsonString());
    }

    @Test
    public void testWithCriticalFailure() {
        List<Dependency> dependencies = Arrays.asList(criticalFailure);
        Health health = new Health(dependencies);
        health.check();
        assertEquals(Integer.valueOf(503), health.getHttpStatus());
        assertEquals(StatusEnum.FAIL.getStatus(), health.getStatus());
        assertEquals(StatusEnum.FAIL.getMessage(), health.getMessage());
        assertEquals(dependencies, health.getDependencies());

        String expectedJson = "{ \"status\": \"FAIL\", \"message\": \"There is at least one critical dependency out of service\", \"dependencies\": [ { \"name\": \"Critical Failure\", \"isCritical\": true, \"status\": \"FAIL\" } ] }";
        assertEquals(expectedJson, health.asJsonString());
    }

    @Test
    public void testWithNonCriticalFailure() {
        List<Dependency> dependencies = Arrays.asList(nonCriticalFailure);
        Health health = new Health(dependencies);
        health.check();
        assertEquals(Integer.valueOf(207), health.getHttpStatus());
        assertEquals(StatusEnum.PARTIAL.getStatus(), health.getStatus());
        assertEquals(StatusEnum.PARTIAL.getMessage(), health.getMessage());
        assertEquals(dependencies, health.getDependencies());

        String expectedJson = "{ \"status\": \"PARTIAL\", \"message\": \"All critical dependencies are up, but there is at least one non critical dependency out of service\", \"dependencies\": [ { \"name\": \"Non Critical Failure\", \"isCritical\": false, \"status\": \"FAIL\" } ] }";
        assertEquals(expectedJson, health.asJsonString());
    }
}
