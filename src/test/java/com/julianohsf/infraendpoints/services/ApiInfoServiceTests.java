package com.julianohsf.infraendpoints.services;

import com.julianohsf.infraendpoints.domains.apiinfo.ApiInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(JUnit4.class)
public class ApiInfoServiceTests {

    @Test
    public void searchManifestFailedByFileName() throws Exception {
        try {
            ApiInfoService service = new ApiInfoService("META-INF/MANIFEST.MF", "test-application-2");
            fail("Expected to throws IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("No manifest files founded at META-INF/MANIFEST.MF with applicationName test-application-2", e.getMessage());
        }
    }

    @Test
    public void searchManifestFailedByApplicationName() throws Exception {
        try {
            ApiInfoService service = new ApiInfoService("META-INF/MANUFACTURE.MF", "test-application");
            fail("Expected to throws IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("No manifest files founded at META-INF/MANUFACTURE.MF with applicationName test-application", e.getMessage());
        }
    }

    @Test
    public void searchManifest() throws Exception {
        ApiInfoService service = new ApiInfoService("META-INF/MANIFEST.MF", "test-application");
        ApiInfo apiInfo = service.getInfo();
        assertEquals("test-application", apiInfo.getApplicationName());
        assertEquals("Zoop Builder Tool", apiInfo.getCreatedBy());
        assertEquals("1.8.0_161", apiInfo.getBuildJdk());
        assertEquals("2.0.2.RELEASE", apiInfo.getSpringBootVersion());
        assertEquals("20180512-7", apiInfo.getBuildNumber());
        assertEquals("1.0.0", apiInfo.getVersion());
    }

}