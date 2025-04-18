package com.WebGenerator.App.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

@Setter
public class WebSiteDto {
    private Long id;
    private String title;
    private String text;
    private Integer plan;
    private String dataCouple;
    @JsonProperty("music_url")
    private String musicUrl;
    @JsonProperty("is_active")
    private Integer isActive;
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Integer getPlan() {
        return plan;
    }

    public String getDataCouple() {
        return dataCouple;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public Integer getIsActive() {
        return isActive;
    }
}
