package org.example.chattrilogy.domain.dto;

import org.example.chattrilogy.domain.User;

public class ResLoginDTO {
    private String token;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {this.user = user;}

    private User user;
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
