package com.gamemash.controller;

import com.gamemash.entity.Player;
import com.gamemash.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // allow frontend access
public class LoginController {

    @Autowired
    private PlayerRepository playerRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Player loginRequest) {
        System.out.println("Login Request Received:");
        System.out.println("Email: " + loginRequest.getEmail());
        System.out.println("Password: " + loginRequest.getPassword());

        if (loginRequest.getEmail() == null || loginRequest.getPassword() == null ||
            loginRequest.getEmail().isEmpty() || loginRequest.getPassword().isEmpty()) {
            System.out.println("Missing email or password");
            return ResponseEntity.badRequest().body("Missing email or password");
        }

        Optional<Player> player = playerRepository.findByEmailAndPassword(
                loginRequest.getEmail(), loginRequest.getPassword());

        if (player.isPresent()) {
            Player p = player.get();
            System.out.println("Login successful for: " + loginRequest.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("firstName", p.getFirstName());
            response.put("avatarid", p.getAvatarid());

            return ResponseEntity.ok(response);
        } else {
            System.out.println("Invalid credentials for: " + loginRequest.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/signup")
    public String signup(@RequestBody Map<String, Object> payload) {
        String email = (String) payload.get("email");
        String password = (String) payload.get("password");
        String firstName = (String) payload.get("firstName");
        String lastName = (String) payload.get("lastName");
        Integer avatarid = (Integer) payload.get("avatarid");

        System.out.println("Signup Request Received:");
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Avatar ID: " + avatarid);

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            System.out.println("Missing email or password");
            return "Missing email or password";
        }

        Optional<Player> existing = playerRepository.findByEmail(email);
        if (existing.isPresent()) {
            System.out.println("Email already in use: " + email);
            return "Email already in use";
        }

        Player newPlayer = new Player();
        newPlayer.setEmail(email);
        newPlayer.setPassword(password);
        newPlayer.setFirstName(firstName);
        newPlayer.setLastName(lastName);
        newPlayer.setAvatarid(avatarid);

        playerRepository.save(newPlayer);
        System.out.println("Signup successful for: " + email);
        return "Signup successful";
    }

    @GetMapping("/player/{email}")
    public ResponseEntity<?> getPlayerByEmail(@PathVariable String email) {
        Optional<Player> player = playerRepository.findByEmail(email);
        if (player.isPresent()) {
            return ResponseEntity.ok(player.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player not found");
        }
    }

    @PutMapping("/player/update")
    public ResponseEntity<?> updatePlayer(@RequestBody Map<String, Object> payload) {
        String email = (String) payload.get("email");
        String firstName = (String) payload.get("firstName");
        String lastName = (String) payload.get("lastName");
        Integer avatarid = (Integer) payload.get("avatarid");

        Optional<Player> optionalPlayer = playerRepository.findByEmail(email);
        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            player.setFirstName(firstName);
            player.setLastName(lastName);
            player.setAvatarid(avatarid);

            playerRepository.save(player);
            return ResponseEntity.ok("Profile updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player not found");
        }
    }
}