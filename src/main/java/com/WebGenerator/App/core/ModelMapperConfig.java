package com.WebGenerator.App.core;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper factoryModelMapper(){
        return new ModelMapper();
    }
}
