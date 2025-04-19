package com.WebGenerator.App.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class RegistrationDto {
    @Valid
    @NotNull(message = "Usuário é obrigatório")
    @JsonProperty("user")
    private UserDto userDto;
    @Valid
    @NotNull(message = "WebSite é obrigatório")
    @JsonProperty("website")
    private WebSiteDto webSiteDto;


    public UserDto getUserDto() {
        return userDto;
    }

    public RegistrationDto setUserDto(UserDto userDto) {
        this.userDto = userDto;
        return this;
    }

    public WebSiteDto getWebSiteDto() {
        return webSiteDto;
    }

    public RegistrationDto setWebSiteDto(WebSiteDto webSiteDto) {
        this.webSiteDto = webSiteDto;
        return this;
    }
}
