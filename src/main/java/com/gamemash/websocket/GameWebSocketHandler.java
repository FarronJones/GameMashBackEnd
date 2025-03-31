package com.gamemash.websocket;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameWebSocketHandler extends TextWebSocketHandler {
    // List of all connected WebSocket sessions
    private static final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    
    // Map to keep track of each player's game state (position, score, etc.)
    private static final Map<String, Player> playerStates = new HashMap<>();
    
    // Example of game state (you can expand this to a more complex structure)
    public static class Player {
        private String playerId;
        private int playerX = 0;
        private int playerY = 0;
        private int score = 0;
        
        public Player(String playerId) {
            this.playerId = playerId;
        }

        // Getters and setters
        public int getPlayerX() { return playerX; }
        public void setPlayerX(int playerX) { this.playerX = playerX; }
        
        public int getPlayerY() { return playerY; }
        public void setPlayerY(int playerY) { this.playerY = playerY; }

        public int getScore() { return score; }
        public void setScore(int score) { this.score = score; }

        public String getPlayerId() { return playerId; }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Add the new player to the sessions list and create a new Player object
        String playerId = session.getId(); // Assuming each session is a unique player
        sessions.add(session);
        playerStates.put(playerId, new Player(playerId));
        sendGameStateToPlayer(session, playerId); // Send the initial state to the player
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String action = message.getPayload();
        String playerId = session.getId(); // Get the player ID from the session

        // Update the game state for the player
        updatePlayerState(playerId, action);

        // Send the updated game state to all players
        broadcastGameState();
    }

    private void updatePlayerState(String playerId, String action) {
        Player player = playerStates.get(playerId);
        if (player == null) return;

        switch (action.toLowerCase()) {
            case "w": player.setPlayerY(player.getPlayerY() - 1); break;
            case "s": player.setPlayerY(player.getPlayerY() + 1); break;
            case "a": player.setPlayerX(player.getPlayerX() - 1); break;
            case "d": player.setPlayerX(player.getPlayerX() + 1); break;
            // Other game actions can be added here
        }
    }

    private void sendGameStateToPlayer(WebSocketSession session, String playerId) throws IOException {
        Player player = playerStates.get(playerId);
        if (player == null) return;

        // Format the game state as JSON to send to the client
        String state = "{\"playerId\":\"" + playerId + "\",\"playerX\":" + player.getPlayerX() + ",\"playerY\":" + player.getPlayerY() + ",\"score\":" + player.getScore() + "}";
        session.sendMessage(new TextMessage(state));
    }

    private void broadcastGameState() throws IOException {
        for (WebSocketSession session : sessions) {
            String playerId = session.getId();
            sendGameStateToPlayer(session, playerId);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Remove the player from the sessions list and player states map
        String playerId = session.getId();
        sessions.remove(session);
        playerStates.remove(playerId);
    }
}