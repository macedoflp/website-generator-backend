package com.WebGenerator.App.config;

import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class ImageKitConfig {

    @Bean
    public ImageKit imageKit(ImageKitProperties properties) {

        ImageKit imageKit = ImageKit.getInstance();

        Configuration config = new Configuration(
            properties.getPublicKey(),
            properties.getPrivateKey(),
            properties.getUrlEndpoint()
        );

        // Define a configuração
        imageKit.setConfig(config);
        return imageKit;
    }
}