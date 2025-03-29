package org.example.chattrilogy.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String password;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy hh:mm:ss a", timezone = "UTC")
    private Date birthday;
    private String profileImageUrl;
    @JsonProperty("isOnline")
    private Boolean isOnline;
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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String toString() {
        return "User [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + ", profileImageUrl=" + profileImageUrl + ", isOnline=" + isOnline + ", lastSeen=" + lastSeen + "]";
    }
}
