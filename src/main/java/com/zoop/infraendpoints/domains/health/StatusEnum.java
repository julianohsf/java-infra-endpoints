package com.zoop.infraendpoints.domains.health;

public enum StatusEnum {

    UP("All dependencies are up", 200),
    PARTIAL("All critical dependencies are up, but there is at least one non critical dependency out of service.", 207),
    FAIL("There is at least one critical dependency out of service", 503);

    private final String message;
    private final Integer httpStatus;

    StatusEnum(String message, Integer httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return name();
    }

    public Boolean isFail() {
        return FAIL.equals(this);
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }
}
