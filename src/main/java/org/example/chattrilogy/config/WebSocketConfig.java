package org.example.chattrilogy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final UserStatusWebSocketHandler userStatusWebSocketHandler;

    public WebSocketConfig(UserStatusWebSocketHandler userStatusWebSocketHandler) {
        this.userStatusWebSocketHandler = userStatusWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ChatWebSocketHandler(), "/chat/{userId}")
                .setAllowedOrigins("*");
        registry.addHandler(new FriendRequestWebSocketHandler(), "/request")
                .setAllowedOrigins("*");
        registry.addHandler(userStatusWebSocketHandler, "/status/{userId}")
                .setAllowedOrigins("*");
    }
}
