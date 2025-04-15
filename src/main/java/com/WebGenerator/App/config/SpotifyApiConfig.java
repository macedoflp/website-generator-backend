package com.WebGenerator.App.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.michaelthelin.spotify.SpotifyApi;

@Configuration
public class SpotifyApiConfig {

    @Bean
    public SpotifyApi spotifyApi(SpotifyApiProperties properties){
        return new SpotifyApi.Builder()
                .setAccessToken("BQD-UO-aoAaRI2ymcNBpZBCMHO1HbAjo-t-T7khUNpcRtWZe62mXRkCW4fobSifEH8mxvMPqUNO-dapuDHJi5FwjZ44LY6yh3x-G5RMMJsBP8YhSeUOKeJybFRgycklW5Y3AHvlqHjY")
                .build();
    }
}
