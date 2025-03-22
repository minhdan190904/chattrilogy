package org.example.chattrilogy.domain.dto;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}