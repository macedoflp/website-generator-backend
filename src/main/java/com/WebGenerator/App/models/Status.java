package com.WebGenerator.App.models;

public enum Status {
    ACTIVE(1),
    INACTIVE(0);

    private final Integer value;

    Status(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
