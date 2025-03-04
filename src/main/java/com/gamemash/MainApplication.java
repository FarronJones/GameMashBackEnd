package com.gamemash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
        System.out.println("The Spring Boot Application is Running!");
    }
    
    @PostConstruct
    public void onStartup() {
    	System.out.println("Application running on http://localhost:8081/ ");
    }
}
