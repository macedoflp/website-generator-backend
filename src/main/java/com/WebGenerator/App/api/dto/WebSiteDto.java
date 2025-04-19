package com.WebGenerator.App.api.dto;

import com.WebGenerator.App.domain.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

@Setter
public class WebSiteDto {
    private Long id;
    private String title;
    private String text;
    private Integer plan;
    @JsonProperty("data_couple")
    private String dataCouple;
    @JsonProperty("music_url")
    private String musicUrl;
    @JsonProperty("is_active")
    private Integer isActive;
    @JsonProperty("website_url")
    private String urlWebSite;

    private User user_app;

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

    public void setUser(User user) {
        this.user_app = user;
    }

    public User getUser() {
        return user_app;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlWebSite() {
        return urlWebSite;
    }

    public void setUrlWebSite(String urlWebSite) {
        this.urlWebSite = urlWebSite;
    }
}
