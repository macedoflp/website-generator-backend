package com.WebGenerator.App.domain.model;

import com.WebGenerator.App.domain.model.util.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Setter
@Getter
@Entity
@Table(name = "websites")
public class WebSite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Integer plan;

    @Column(nullable = false)
    private String dataCouple;

    @Column(name = "music_url")
    private String musicUrl;

    @Column(name = "website_url")
    private String urlWebSite;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "is_active")
    private Status isActive = Status.ACTIVE;

    @OneToMany(cascade = ALL, mappedBy = "website")
    private List<Img> imgs  = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user_app;

    @Column(nullable = false)
    private String idiom;

    public String getIdiom() {
        return idiom;
    }

    public void setIdiom(String idiom) {
        this.idiom = idiom;
    }

    public void addImg(Img img){
        this.imgs.add(img);
    }

    public void setUser(User user) {
        this.user_app = user;
    }

    public List<Img> getImgs() {
        return imgs;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPlan(Integer plan) {
        this.plan = plan;
    }

    public void setDataCouple(String dataCouple) {
        this.dataCouple = dataCouple;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public void setIsActive(Status isActive) {
        this.isActive = isActive;
    }

    public String getUrlWebSite() {
        return urlWebSite;
    }

    public void setUrlWebSite(String urlWebSite) {
        this.urlWebSite = urlWebSite;
    }

    public String getTitle() {
        return title;
    }
}
