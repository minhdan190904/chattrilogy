package org.example.chattrilogy.config;

import com.nimbusds.jose.shaded.gson.Gson;
import io.micrometer.common.lang.NonNullApi;
import org.example.chattrilogy.domain.dto.MessageDTO;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.example.chattrilogy.util.HelperUtils.checkNumberPositive;

@NonNullApi
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentHashMap<Integer, CopyOnWriteArrayList<WebSocketSession>> userSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Integer userId = getReceiverIdFromSession(session);
        if (userId != null) {
            userSessions.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>()).add(session);
            System.out.println("User " + userId + " connected. Total sessions: " + userSessions.get(userId).size());
            printAllSessions();
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        MessageDTO messageDTO = new Gson().fromJson(message.getPayload(), MessageDTO.class);
        Integer receiverId = messageDTO.getReceiverId();
        Integer senderId = messageDTO.getMessage().getSender().getId();

        sendMessageToUser(receiverId, message);
        sendMessageToUser(senderId, message);

        System.out.println("Message processed. Current session details:");
        printAllSessions();
    }

    private void sendMessageToUser(Integer userId, TextMessage message) {
        if (userId != null) {
            CopyOnWriteArrayList<WebSocketSession> sessionsForUser = userSessions.get(userId);
            if (sessionsForUser != null && !sessionsForUser.isEmpty()) {
                System.out.println("📨 Sending message to User ID: " + userId + " | Active Sessions: " + sessionsForUser.size());

                for (WebSocketSession session : sessionsForUser) {
                    if (session.isOpen()) {
                        try {
                            session.sendMessage(message);
                            System.out.println("✅ Sent to Session ID: " + session.getId());
                        } catch (IOException e) {
                            System.out.println("❌ Failed to send message to Session ID: " + session.getId());
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("⚠️ Skipped closed session: " + session.getId());
                    }
                }
            } else {
                System.out.println("❌ No active sessions found for User ID: " + userId);
            }
        }
    }


    private Integer getReceiverIdFromSession(WebSocketSession session) {
        if (session.getUri() == null) {
            return null;
        }
        String path = session.getUri().getPath();
        String[] parts = path.split("/");
        String receiverIdRaw = parts.length > 1 ? parts[parts.length - 1] : null;
        if (receiverIdRaw != null) {
            if (checkNumberPositive(receiverIdRaw)) {
                return Integer.parseInt(receiverIdRaw);
            }
        }
        return null;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Integer userId = getReceiverIdFromSession(session);
        if (userId != null) {
            CopyOnWriteArrayList<WebSocketSession> receiverSessions = userSessions.get(userId);
            if (receiverSessions != null) {
                receiverSessions.remove(session);
                System.out.println("User " + userId + " disconnected. Remaining sessions: " + receiverSessions.size());
                if (receiverSessions.isEmpty()) {
                    userSessions.remove(userId);
                }
            }
            printAllSessions();
        }
    }

    private void printAllSessions() {
        System.out.println("🟢 ===== Active WebSocket Sessions ===== 🟢");
        if (userSessions.isEmpty()) {
            System.out.println("❌ No active WebSocket connections.");
        } else {
            for (Integer userId : userSessions.keySet()) {
                CopyOnWriteArrayList<WebSocketSession> sessions = userSessions.get(userId);
                System.out.println("👤 User ID: " + userId + " | 🔗 Active Sessions: " + sessions.size());
                for (WebSocketSession session : sessions) {
                    String statusIcon = session.isOpen() ? "✅" : "❌";
                    System.out.println("   🔹 Session ID: " + session.getId() + " | Status: " + statusIcon);
                }
            }
        }
        System.out.println("🔻 ===================================== 🔻");
    }

}
