package org.example.chattrilogy.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class UserDeviceToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String timeStamp;

    public UserDeviceToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.timeStamp = LocalDateTime.now().toString();
    }


    public UserDeviceToken() {
        this.token = "";
        this.timeStamp = LocalDateTime.now().toString();
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
