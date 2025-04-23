package com.WebGenerator.App.api.dto;

public record LoginUserDto(
        String email,
        String generatedCode
) {
}
