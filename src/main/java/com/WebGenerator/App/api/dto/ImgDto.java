package com.WebGenerator.App.api.dto;

import jakarta.validation.constraints.NotEmpty;

public class ImgDto {
    @NotEmpty
    private String imgUrl;
}
