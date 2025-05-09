package com.WebGenerator.App.domain.model.util;

public enum Categoria {
    MAES(0),
    NAMORADOS(1);

    private final Integer value;

    Categoria(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
