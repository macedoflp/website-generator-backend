package com.WebGenerator.App.api.dto;

import com.WebGenerator.App.domain.model.util.RoleName;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDto {
    private Long id;
    @NotNull @NotBlank @Size(max=64)
    @Pattern(regexp = "[A-zÀ-ú ]+")
    private String name;

    @Size(max = 100)@NotNull @NotBlank
    private String email;
    @JsonProperty("generated_code")
    private String generatedCode;

    @Enumerated(EnumType.STRING)
    private RoleName role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeneratedCode() {
        return generatedCode;
    }

    public void setGeneratedCode(String generatedCode) {
        this.generatedCode = generatedCode;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }
}
