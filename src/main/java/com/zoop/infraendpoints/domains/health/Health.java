package com.zoop.infraendpoints.domains.health;

import java.util.List;
import java.util.stream.Collectors;

public class Health {

    private String status;
    private String message;
    private List<Dependency> dependencies;
    private Integer httpStatus;

    public Health(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public Health check() {
        this.dependencies.forEach(Dependency::clearStatus);

        StatusEnum status = StatusEnum.UP;
        List<Dependency> failedDependencies = this.dependencies.parallelStream()
                                                               .map(Dependency::checkStatus)
                                                               .filter(dependency -> dependency.getStatus().isFail())
                                                               .collect(Collectors.toList());

        if (!failedDependencies.isEmpty()) {
            Long criticalDependenciesCounter = failedDependencies.stream().filter(d -> d.isCritical()).count();
            status = criticalDependenciesCounter > 0 ? StatusEnum.FAIL : StatusEnum.PARTIAL;
        }

        this.status = status.getStatus();
        this.message = status.getMessage();
        this.httpStatus = status.getHttpStatus();

        return this;
    }

    public String asJsonString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ")
          .append("\"status\": \"").append(this.status).append("\", ")
          .append("\"message\": \"").append(this.message).append("\", ")
          .append("\"dependencies\": [ ");

        for (Dependency dependency : dependencies) {
            sb.append(dependency.asJsonString());
        }

        return sb.append(" ] }").toString();
    }
}