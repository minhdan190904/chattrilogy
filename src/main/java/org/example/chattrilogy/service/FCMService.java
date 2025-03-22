package org.example.chattrilogy.service;

import com.google.firebase.messaging.*;
import org.example.chattrilogy.domain.dto.NotificationRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class FCMService {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Async
    public void sendMessageToToken(NotificationRequest request) {
        try {
            Message message = getPreconfiguredMessageToToken(request);
            String jsonOutput = gson.toJson(message);
            String response = sendAndGetResponse(message);
            log.info("✅ Sent FCM message to token: {}, Response: {}, Payload: {}",
                    request.getToken(), response, jsonOutput);
        } catch (InterruptedException | ExecutionException e) {
            log.error("❌ Failed to send FCM message to token: {}, Error: {}",
                    request.getToken(), e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis())
                .setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setTag(topic)
                        .build())
                .build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder()
                        .setCategory(topic)
                        .setThreadId(topic)
                        .build())
                .build();
    }

    private Message getPreconfiguredMessageToToken(NotificationRequest request) {
        return getPreconfiguredMessageBuilder(request)
                .setToken(request.getToken())
                .build();
    }

    private Message.Builder getPreconfiguredMessageBuilder(NotificationRequest request) {
        return Message.builder()
                .setAndroidConfig(getAndroidConfig(request.getTopic()))
                .setApnsConfig(getApnsConfig(request.getTopic()))
                .setNotification(Notification.builder()
                        .setTitle(request.getTitle())
                        .setBody(request.getBody())
                        .setImage(request.getImageUrl())
                        .build());
    }
}
