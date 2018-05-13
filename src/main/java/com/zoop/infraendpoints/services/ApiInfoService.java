package com.zoop.infraendpoints.services;

import com.zoop.infraendpoints.domains.apiinfo.ApiInfo;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.jar.Manifest;

public class ApiInfoService {

    private ApiInfo apiInfo;

    public ApiInfoService(String manifestFilePath, String applicationName) throws IOException {
        this.apiInfo = loadApiInfo(manifestFilePath, applicationName);
    }

    public ApiInfo getInfo() {
        return apiInfo;
    }

    private ApiInfo loadApiInfo(String manifestFilePath, String applicationName) throws IOException {
        for (URL url : Collections.list(getClass().getClassLoader().getResources(manifestFilePath))) {
            Manifest manifest = new Manifest(url.openStream());

            if (manifest.getMainAttributes().containsValue(applicationName)) {
                return new ApiInfo(manifest.getMainAttributes());
            }
        }

        throw new IllegalArgumentException("No manifest files founded at "+manifestFilePath+" with applicationName "+applicationName);
    }

}
