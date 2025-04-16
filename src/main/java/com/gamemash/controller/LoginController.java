package com.gamemash.controller;

import com.gamemash.entity.Player;
import com.gamemash.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // allow frontend access
public class LoginController {

    @Autowired
    private PlayerRepository playerRepository;

    @PostMapping("/login")
    public String login(@RequestBody Player loginRequest) {
        System.out.println("Login Request Received:");
        System.out.println("Email: " + loginRequest.getEmail());
        System.out.println("Password: " + loginRequest.getPassword());

        if (loginRequest.getEmail() == null || loginRequest.getPassword() == null ||
            loginRequest.getEmail().isEmpty() || loginRequest.getPassword().isEmpty()) {
            System.out.println("Missing email or password");
            return "Missing email or password";
        }

        Optional<Player> player = playerRepository.findByEmailAndPassword(
                loginRequest.getEmail(), loginRequest.getPassword());

        if (player.isPresent()) {
            System.out.println("Login successful for: " + loginRequest.getEmail());
            return "Login successful";
        } else {
            System.out.println("Invalid credentials for: " + loginRequest.getEmail());
            return "Invalid credentials";
        }
    }

    @PostMapping("/signup")
    public String signup(@RequestBody Player newPlayer) {
        System.out.println("Signup Request Received:");
        System.out.println("Email: " + newPlayer.getEmail());
        System.out.println("Password: " + newPlayer.getPassword());

        if (newPlayer.getEmail() == null || newPlayer.getPassword() == null ||
            newPlayer.getEmail().isEmpty() || newPlayer.getPassword().isEmpty()) {
            System.out.println("Missing email or password");
            return "Missing email or password";
        }

        Optional<Player> existing = playerRepository.findByEmail(newPlayer.getEmail());
        if (existing.isPresent()) {
            System.out.println("Email already in use: " + newPlayer.getEmail());
            return "Email already in use";
        }

        playerRepository.save(newPlayer);
        System.out.println("Signup successful for: " + newPlayer.getEmail());
        return "Signup successful";
    }
}