package com.WebGenerator.App.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user_app")
    @JsonManagedReference
    private List<WebSite> webSites = new ArrayList<>();

    public void addSite(WebSite webSite){
        this.webSites.add(webSite);
    }

    public List<WebSite> getWebSites() {
        return webSites;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWebSites(List<WebSite> webSites) {
        this.webSites = webSites;
    }
}
