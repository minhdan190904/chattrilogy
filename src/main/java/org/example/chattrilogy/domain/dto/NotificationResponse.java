package org.example.chattrilogy.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotificationResponse {
    private int status;
    private String message;

    public NotificationResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

}