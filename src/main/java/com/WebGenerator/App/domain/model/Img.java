package com.WebGenerator.App.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "imgs")
public class Img {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="website_id")
    private WebSite website;

    @Column(name = "img_url")
    private String imgUrl;

    public Long getId() {
        return id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setWebsite(WebSite website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "Img{" +
                "id=" + id +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
