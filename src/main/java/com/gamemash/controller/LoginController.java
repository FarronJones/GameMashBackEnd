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
        Optional<Player> player = playerRepository.findByEmailAndPassword(
                loginRequest.getEmail(), loginRequest.getPassword());

        if (player.isPresent()) {
            return "Login successful";
        } else {
            return "Invalid credentials";
        }
    }

    @PostMapping("/signup")
    public String signup(@RequestBody Player newPlayer) {
        Optional<Player> existing = playerRepository.findByEmail(newPlayer.getEmail());
        if (existing.isPresent()) {
            return "Email already in use";
        }

        playerRepository.save(newPlayer);
        return "Signup successful";
    }
}