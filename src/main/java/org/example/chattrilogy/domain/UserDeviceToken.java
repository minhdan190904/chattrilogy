package org.example.chattrilogy.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class UserDeviceToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    @Getter
    private String token;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter
    @Getter
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
}
