package com.example.servicesprovider.config;

import com.example.servicesprovider.utility.EntityValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validator;

@Configuration
public class AppConfig {
    EntityValidator entityValidator;

    public AppConfig(EntityValidator entityValidator) {
        this.entityValidator = entityValidator;
    }

    @Bean
    public Validator getValidator() {
        return entityValidator.validator;
    }
}
