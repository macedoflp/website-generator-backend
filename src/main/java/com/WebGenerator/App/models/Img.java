package com.WebGenerator.App.models;

import jakarta.persistence.*;

@Entity
@Table(name = "imgs")
public class Img {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgId;

    private String imgUrl;

    public Long getImgId() {
        return imgId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
