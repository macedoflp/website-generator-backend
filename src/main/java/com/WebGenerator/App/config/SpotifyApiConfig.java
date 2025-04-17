package com.WebGenerator.App.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;

@JsonDeserialize(builder = GetTrackRequest.Builder.class)
@Configuration
public class SpotifyApiConfig{

    @Bean
    public SpotifyApi spotifyApi(SpotifyApiProperties properties){
        return new SpotifyApi.Builder()
                .setClientId(properties.getClientId())
                .setClientSecret(properties.getClientSecret())
                .build();
    }
}
