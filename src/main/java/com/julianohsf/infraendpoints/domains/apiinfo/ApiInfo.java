package com.julianohsf.infraendpoints.domains.apiinfo;

import java.util.jar.Attributes;

public class ApiInfo {

    private String applicationName;
    private String createdBy;
    private String version;
    private String buildNumber;
    private String buildJdk;
    private String springBootVersion;

    public ApiInfo(Attributes attributes) {
        this.applicationName = attributes.getValue("Application-Name");
        this.createdBy = attributes.getValue("Created-By");
        this.buildJdk = attributes.getValue("Build-Jdk");
        this.springBootVersion = attributes.getValue("Spring-Boot-Version");
        this.buildNumber = attributes.getValue("Implementation-Build");
        this.version = attributes.getValue("Implementation-Version");
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getVersion() {
        return version;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public String getBuildJdk() {
        return buildJdk;
    }

    public String getSpringBootVersion() {
        return springBootVersion;
    }


    public String asJsonString() {
        StringBuilder sb = new StringBuilder();
        return sb.append("{ ")
                .append("\"applicationName\": \"").append(this.applicationName).append("\", ")
                .append("\"createdBy\": \"").append(this.createdBy).append("\", ")
                .append("\"version\": \"").append(this.version).append("\", ")
                .append("\"buildNumber\": \"").append(this.buildNumber).append("\", ")
                .append("\"buildJdk\": \"").append(this.buildJdk).append("\", ")
                .append("\"springBootVersion\": \"").append(this.springBootVersion)
                .append("\" }")
                .toString();
    }
}
