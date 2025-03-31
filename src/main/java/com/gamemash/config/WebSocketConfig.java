package com.gamemash.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

import com.gamemash.websocket.GameWebSocketHandler;
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new GameWebSocketHandler(), "/game").setAllowedOrigins("*");
   }
}