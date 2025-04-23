package com.WebGenerator.App.api.dto;

import com.WebGenerator.App.domain.model.Role;

import java.util.List;

public record RecoveryUserDto(
        Long id,
        String email,
        List<Role> roles
) {
}
