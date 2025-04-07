package com.WebGenerator.App.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebSiteDto {
    private Long id;
    private String title;
    private String text;
    private Integer plan;
    @JsonProperty("music_url")
    private String musicUrl;
    @JsonProperty("is_active")
    private Integer isActive;
}
