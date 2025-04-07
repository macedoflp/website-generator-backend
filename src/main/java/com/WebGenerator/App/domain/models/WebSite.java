package com.WebGenerator.App.domain.models;

import com.WebGenerator.App.domain.models.util.Status;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "websites")
public class WebSite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Integer plan;

    @Column(name = "music_url")
    private String musicUrl;

    @Column(columnDefinition = "INTEGER DEFAULT 1", name = "is_active")
    private Integer isActive = Status.ACTIVE.getValue();

    @OneToMany(cascade = ALL, mappedBy = "website")
    private List<Img> imgs  = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPlan() {
        return plan;
    }

    public void setPlan(Integer plan) {
        this.plan = plan;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public List<Img> getImgs() {
        return imgs;
    }

    public void setImgs(List<Img> imgs) {
        this.imgs = imgs;
    }

}
