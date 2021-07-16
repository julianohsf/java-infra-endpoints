package com.julianohsf.infraendpoints.domains.health;

import java.util.function.Supplier;

public class Dependency {

    private String name;
    private Boolean isCritical;
    private Supplier<Boolean> checkStatus;

    private StatusEnum status;

    public Dependency(String name, Boolean isCritical, Supplier<Boolean> checkStatus) {
        this.name = name;
        this.isCritical = isCritical;
        this.checkStatus = checkStatus;
    }

    public Dependency checkStatus() {
        try {
            this.status = this.checkStatus.get() ? StatusEnum.UP : StatusEnum.FAIL;
        } catch (Exception e) {
            this.status = StatusEnum.FAIL;
        }

        return this;
    }

    public void clearStatus() {
        this.status = null;
    }

    public String getName() {
        return name;
    }

    public Boolean isCritical() {
        return isCritical;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public String asJsonString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ")
                .append("\"name\": \"").append(this.name).append("\", ")
                .append("\"isCritical\": ").append(this.isCritical.toString()).append(", ")
                .append("\"status\": \"").append(this.status).append("\" }");
        return sb.toString();
    }
}
