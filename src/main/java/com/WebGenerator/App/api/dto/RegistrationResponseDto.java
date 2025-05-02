package com.WebGenerator.App.api.dto;

public record RegistrationResponseDto(
        WebSiteDto webSite,
        RecoveryJwtTokenDto jwtToken
) {
}
