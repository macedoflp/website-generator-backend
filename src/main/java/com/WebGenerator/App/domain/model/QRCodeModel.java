package com.WebGenerator.App.domain.model;

public enum QRCodeModel {
    DEFAULT, ELEGANT, MINIMALIST;

    public String getTemplateName() {
        return this.name().toLowerCase();
    }
}
