package com.WebGenerator.App.domain.model;

import com.WebGenerator.App.domain.model.util.Status;
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

}
