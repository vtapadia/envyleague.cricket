package com.envyleague.cricket.domain;

public enum Status {
    ACTIVE("Registered"),
    PENDING("Pending Approval"),
    CANCELLED("Rejected");

    private String description;
    private Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
