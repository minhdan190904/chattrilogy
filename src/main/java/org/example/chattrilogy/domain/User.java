package org.example.chattrilogy.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private String password;

    @Setter
    @Getter
    private String email;

    @Setter
    @Getter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy hh:mm:ss a", timezone = "UTC")
    private Date birthday;

    @Setter
    @Getter
    private String profileImageUrl;

    @Getter
    @Setter
    @JsonProperty("isOnline")
    private Boolean isOnline;

    @Setter
    @Getter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy hh:mm:ss a", timezone = "UTC")
    private Date lastSeen;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<FriendRequest> sentRequests;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<FriendRequest> receivedRequests;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Message> messages;

    @OneToMany(mappedBy = "user1", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Chat> chats1;

    @OneToMany(mappedBy = "user2", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Chat> chats2;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UserDeviceToken> userDeviceTokens;

    public User(String name, String password, String email, Date birthday, String profileImageUrl, Boolean isOnline, Date lastSeen) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.profileImageUrl = profileImageUrl;
        this.isOnline = isOnline;
        this.lastSeen = lastSeen;
    }

    public User() {
    }

    public String toString() {
        return "User [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + ", profileImageUrl=" + profileImageUrl + ", isOnline=" + isOnline + ", lastSeen=" + lastSeen + "]";
    }
}
