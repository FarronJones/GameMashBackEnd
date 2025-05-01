package com.gamemash.websocket;

import audio.AudioPlayer;
import entities.Player;
import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import utilz.GameConfig;

import java.io.IOException;

@Component
public class GameWebSocketHandler extends TextWebSocketHandler {

    private final AudioPlayer audioPlayer = new AudioPlayer();
    private Playing playing;

    public GameWebSocketHandler() {
        Gamestate.state = Gamestate.MENU;
    }

    private void initPlayingIfNeeded() {
        if (playing == null) {
            // ⚠️ Replace this with a real Game instance if needed
            Game game = new Game(); 
            this.playing = new Playing(game);
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        JSONObject json = new JSONObject(payload);
        String action = json.optString("action", "");

        switch (action) {
            case "startGame" -> {
                initPlayingIfNeeded();
                Gamestate.state = Gamestate.PLAYING;
                audioPlayer.playSong(AudioPlayer.Level_1);
            }
            case "pause" -> {
                initPlayingIfNeeded();
                playing.setGameOver(true);
            }
            case "unpause" -> {
                initPlayingIfNeeded();
                playing.unpauseGame();
            }
            case "resetGame" -> {
                initPlayingIfNeeded();
                playing.resetAll();
                Gamestate.state = Gamestate.PLAYING;
            }
            case "returnToMenu" -> {
                initPlayingIfNeeded();
                playing.resetAll();
                Gamestate.state = Gamestate.MENU;
                audioPlayer.playSong(AudioPlayer.MENU_1);
            }
            case "moveLeft" -> {
                initPlayingIfNeeded();
                if (Gamestate.state == Gamestate.PLAYING) {
                    Player player = playing.getPlayer();
                    player.setLeft(true);
                    player.setRight(false);
                }
            }
            case "moveRight" -> {
                initPlayingIfNeeded();
                if (Gamestate.state == Gamestate.PLAYING) {
                    Player player = playing.getPlayer();
                    player.setRight(true);
                    player.setLeft(false);
                }
            }
            case "jump" -> {
                initPlayingIfNeeded();
                if (Gamestate.state == Gamestate.PLAYING) {
                    Player player = playing.getPlayer();
                    player.setJump(true);
                    audioPlayer.playEffect(AudioPlayer.JUMP);
                }
            }
            case "stop" -> {
                initPlayingIfNeeded();
                playing.getPlayer().resetDirBooleans();
            }
            case "toggleMusic" -> audioPlayer.toggleSongMute();
            case "toggleSfx" -> audioPlayer.toggleEffectMute();
            case "setVolume" -> {
                float volume = (float) json.optDouble("volume", 1.0);
                audioPlayer.setVolume(volume);
            }
            case "getGameState" -> {
                initPlayingIfNeeded();
                sendJson(session, getGameStateJson());
                return;
            }
            case "getGameConfig" -> {
                sendJson(session, getGameConfigJson());
                return;
            }
            default -> sendMessage(session, "Unknown action: " + action);
        }

        if (Gamestate.state == Gamestate.PLAYING) {
            initPlayingIfNeeded();
            playing.update();
        }

        sendJson(session, getGameStateJson());
    }

    private JSONObject getGameStateJson() {
        initPlayingIfNeeded();
        Player player = playing.getPlayer();

        JSONObject json = new JSONObject();
        json.put("gamestate", Gamestate.state.toString());
        json.put("gameOver", false);
        json.put("paused", false);

        JSONObject playerJson = new JSONObject();
        playerJson.put("x", player.getHitbox().x);
        playerJson.put("y", player.getHitbox().y);
        playerJson.put("width", player.getHitbox().width);
        playerJson.put("height", player.getHitbox().height);
        json.put("player", playerJson);

        return json;
    }

    private JSONObject getGameConfigJson() {
        JSONObject config = new JSONObject();
        config.put("tileSize", GameConfig.TILES_SIZE);
        config.put("gameWidth", GameConfig.GAME_WIDTH);
        config.put("gameHeight", GameConfig.GAME_HEIGHT);
        return config;
    }

    private void sendMessage(WebSocketSession session, String message) throws IOException {
        JSONObject json = new JSONObject();
        json.put("status", "ok");
        json.put("message", message);
        session.sendMessage(new TextMessage(json.toString()));
    }

    private void sendJson(WebSocketSession session, JSONObject json) throws IOException {
        session.sendMessage(new TextMessage(json.toString()));
    }
}
