package com.gamemash.repository;

import com.gamemash.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
    Optional<Player> findByEmailAndPassword(String email, String password);
    Optional<Player> findByEmail(String email); // for checking existence during signup
}
