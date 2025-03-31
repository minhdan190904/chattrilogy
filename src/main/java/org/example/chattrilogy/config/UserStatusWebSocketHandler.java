package org.example.chattrilogy.config;

import io.micrometer.common.lang.NonNullApi;
import org.example.chattrilogy.repository.UserRepository;
import org.example.chattrilogy.service.UserService;
import org.example.chattrilogy.util.error.IdInvalidException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

import static org.example.chattrilogy.util.HelperUtils.checkNumberPositive;

@NonNullApi
@Component
public class UserStatusWebSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentHashMap<Integer, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    private final UserService userService;

    public UserStatusWebSocketHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Integer userId = getUserIdFromSession(session);
        if (userId != null) {
            userSessions.put(userId, session);
            try{
                userService.updateStatus(userId, true);
                System.out.println("#######User " + userId + " is online.#######");
            } catch (IdInvalidException ex){
                System.out.println("#######Update status failed: " + ex.getMessage() + "#######");
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Integer userId = getUserIdFromSession(session);
        if (userId != null) {
            userSessions.remove(userId);
            try{
                userService.updateStatus(userId, false);
                System.out.println("#######User " + userId + " is offline.#######");
            } catch (IdInvalidException ex){
                System.out.println("#######Update status failed: " + ex.getMessage() + "#######");
            }
        }
    }

    private Integer getUserIdFromSession(WebSocketSession session) {
        String path = session.getUri().getPath();
        String[] parts = path.split("/");
        String userIdRaw = parts.length > 1 ? parts[parts.length - 1] : null;
        if(userIdRaw != null) {
            if(checkNumberPositive(userIdRaw)) {
                return Integer.parseInt(userIdRaw);
            }
        }
        return null;
    }
}
