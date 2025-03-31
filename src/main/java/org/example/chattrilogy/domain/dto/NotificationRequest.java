package org.example.chattrilogy.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotificationRequest {
    private String title;
    private String body;
    private String topic;
    private String token;
    private String imageUrl;

    public NotificationRequest(String title, String body, String topic, String token, String imageUrl) {
        this.title = title;
        this.body = body;
        this.topic = topic;
        this.token = token;
        this.imageUrl = imageUrl;
    }

}