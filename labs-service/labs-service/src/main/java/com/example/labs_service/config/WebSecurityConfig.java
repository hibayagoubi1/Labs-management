package com.example.labs_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebSecurityConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Autoriser tous les chemins
                        .allowedOrigins("http://localhost:4200") // Spécifiez les origines autorisées
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Spécifiez les méthodes HTTP autorisées
                        .allowedHeaders("*") // Autoriser tous les en-têtes
                        .allowCredentials(true); // Autoriser l'envoi des cookies et informations d'authentification
            }
        };
    }
}
