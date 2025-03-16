package org.example.chattrilogy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ChatWebSocketHandler(), "/chat")
                .setAllowedOrigins("*");
        registry.addHandler(new FriendRequestWebSocketHandler(), "/request")
                .setAllowedOrigins("*");
    }
}
