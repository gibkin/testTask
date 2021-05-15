package ru.testing.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings (CorsRegistry registry) {
        registry.addMapping ("/**")
                .allowCredentials (true)
                .allowedHeaders ("Authorization", "Cache-Control", "Content-Type")
                .allowedMethods ("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedOrigins ("*");
    }
}
