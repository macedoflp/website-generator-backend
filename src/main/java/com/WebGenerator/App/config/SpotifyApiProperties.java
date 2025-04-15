package com.WebGenerator.App.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spotifyapi")
public class SpotifyApiProperties {

    private String accesToken;

    public String getAccesToken() {
        System.err.println("Obtendo o token: " + accesToken);
        return accesToken;
    }

    public void setAccesToken(String accesToken) {
        System.err.println("Setando o token: " + accesToken);
        this.accesToken = accesToken;
    }
}
