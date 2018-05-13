package com.zoop.infraendpoints.domains.health;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class DependencyTests {

    @Test
    public void criticalSuccess() {
        Dependency dependency = new Dependency("Critical Success", Boolean.TRUE, () -> Boolean.TRUE);

        assertNull(dependency.getStatus());

        dependency.checkStatus();
        assertEquals("Critical Success", dependency.getName());
        assertEquals(StatusEnum.UP, dependency.getStatus());
        assertTrue(dependency.isCritical());
        assertEquals("{ \"name\": \"Critical Success\", \"isCritical\": true, \"status\": \"UP\" }", dependency.asJsonString());

        dependency.clearStatus();
        assertNull(dependency.getStatus());
    }

    @Test
    public void criticalFail() {
        Dependency dependency = new Dependency("Critical Failure", Boolean.TRUE, () -> Boolean.FALSE);

        assertNull(dependency.getStatus());

        dependency.checkStatus();
        assertEquals("Critical Failure", dependency.getName());
        assertEquals(StatusEnum.FAIL, dependency.getStatus());
        assertTrue(dependency.isCritical());
        assertEquals("{ \"name\": \"Critical Failure\", \"isCritical\": true, \"status\": \"FAIL\" }", dependency.asJsonString());

        dependency.clearStatus();
        assertNull(dependency.getStatus());
    }

    @Test
    public void nonCriticalSuccess() {
        Dependency dependency = new Dependency("Non Critical Success", Boolean.FALSE, () -> Boolean.TRUE);

        assertNull(dependency.getStatus());

        dependency.checkStatus();
        assertEquals("Non Critical Success", dependency.getName());
        assertEquals(StatusEnum.UP, dependency.getStatus());
        assertFalse(dependency.isCritical());
        assertEquals("{ \"name\": \"Non Critical Success\", \"isCritical\": false, \"status\": \"UP\" }", dependency.asJsonString());

        dependency.clearStatus();
        assertNull(dependency.getStatus());
    }

    @Test
    public void nonCriticalFail() {
        Dependency dependency = new Dependency("Non Critical Failure", Boolean.FALSE, () -> Boolean.FALSE);

        assertNull(dependency.getStatus());

        dependency.checkStatus();
        assertEquals("Non Critical Failure", dependency.getName());
        assertEquals(StatusEnum.FAIL, dependency.getStatus());
        assertFalse(dependency.isCritical());
        assertEquals("{ \"name\": \"Non Critical Failure\", \"isCritical\": false, \"status\": \"FAIL\" }", dependency.asJsonString());

        dependency.clearStatus();
        assertNull(dependency.getStatus());
    }

    @Test
    public void criticalThrowsException() {
        Dependency dependency = new Dependency("Critical With Exception", Boolean.TRUE, () -> { throw new RuntimeException("Fail"); });

        assertNull(dependency.getStatus());

        dependency.checkStatus();
        assertEquals("Critical With Exception", dependency.getName());
        assertEquals(StatusEnum.FAIL, dependency.getStatus());
        assertTrue(dependency.isCritical());
        assertEquals("{ \"name\": \"Critical With Exception\", \"isCritical\": true, \"status\": \"FAIL\" }", dependency.asJsonString());

        dependency.clearStatus();
        assertNull(dependency.getStatus());
    }

    @Test
    public void nonCriticalThrowsException() {
        Dependency dependency = new Dependency("Non Critical With Exception", Boolean.FALSE, () -> { throw new RuntimeException("Fail"); });

        assertNull(dependency.getStatus());

        dependency.checkStatus();
        assertEquals("Non Critical With Exception", dependency.getName());
        assertEquals(StatusEnum.FAIL, dependency.getStatus());
        assertFalse(dependency.isCritical());
        assertEquals("{ \"name\": \"Non Critical With Exception\", \"isCritical\": false, \"status\": \"FAIL\" }", dependency.asJsonString());

        dependency.clearStatus();
        assertNull(dependency.getStatus());
    }

}
