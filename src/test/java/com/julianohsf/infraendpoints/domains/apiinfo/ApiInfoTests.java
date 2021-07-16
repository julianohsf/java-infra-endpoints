package com.julianohsf.infraendpoints.domains.apiinfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.jar.Attributes;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ApiInfoTests {

    private Attributes attributes;

    @Before
    public void setUp() {
        this.attributes = new Attributes();
        attributes.putValue("Application-Name", "test-application");
        attributes.putValue("Created-By", "julianohsf Builder Tool");
        attributes.putValue("Build-Jdk", "1.8.0_161");
        attributes.putValue("Spring-Boot-Version", "2.0.2.RELEASE");
        attributes.putValue("Implementation-Build", "20180512-7");
        attributes.putValue("Implementation-Version", "1.0.0");
    }

    @Test
    public void testWithAttributes() {
        ApiInfo apiInfo = new ApiInfo(attributes);
        assertEquals("test-application", apiInfo.getApplicationName());
        assertEquals("julianohsf Builder Tool", apiInfo.getCreatedBy());
        assertEquals("1.8.0_161", apiInfo.getBuildJdk());
        assertEquals("2.0.2.RELEASE", apiInfo.getSpringBootVersion());
        assertEquals("20180512-7", apiInfo.getBuildNumber());
        assertEquals("1.0.0", apiInfo.getVersion());

        assertEquals("{ \"applicationName\": \"test-application\", \"createdBy\": \"julianohsf Builder Tool\", \"version\": \"1.0.0\", \"buildNumber\": \"20180512-7\", \"buildJdk\": \"1.8.0_161\", \"springBootVersion\": \"2.0.2.RELEASE\" }", apiInfo.asJsonString());
    }

}