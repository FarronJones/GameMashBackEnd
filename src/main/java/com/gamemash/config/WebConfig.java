package com.gamemash.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow CORS requests from the frontend (adjust port if necessary)
        registry.addMapping("/api/**")  // Apply this configuration to all API endpoints
                .allowedOrigins("http://localhost:5500") // Allow your frontend URL (e.g., Live Server running on 5500)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow these HTTP methods
                .allowedHeaders("*"); // Allow all headers (you can also specify specific headers)
    }
}
