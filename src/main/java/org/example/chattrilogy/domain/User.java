package org.example.chattrilogy.domain;


import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String password;
    private String email;
    private String profileImageUrl;
    private Boolean isOnline;
    private String lastSeen;

    public User(String name, String password, String email, String profileImageUrl, Boolean isOnline, String lastSeen) {
        this.name = name;
        this.password = password;
        this.email = email;
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

    public String getLastSeen() {
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

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Boolean getOnline() {
        return isOnline;
    }
}
