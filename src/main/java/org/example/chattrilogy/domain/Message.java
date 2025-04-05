package org.example.chattrilogy.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy hh:mm:ss a", timezone = "GMT+07:00")
    private Date sendingTime;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Getter
    @Setter
    @JsonProperty("isSeen")
    private boolean isSeen;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    @JsonBackReference
    private Chat chat;

    public Message(User sender, String message, MessageType messageType, boolean isSeen) {
        this.sender = sender;
        this.message = message;
        this.isSeen = isSeen;
        this.messageType = messageType;
        this.sendingTime = new Date();
    }

    public Message() {
        this.sender = new User();
        this.message = "";
        this.isSeen = false;
        this.messageType = MessageType.TEXT;
        this.sendingTime = new Date();
    }


    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", senderId=" + (sender != null ? sender.getId() : "null") +
                ", message=\"" + message + "\"" +
                ", sendingTime=" + sendingTime +
                ", messageType=" + messageType +
                ", chatId=" + (chat != null ? chat.getId() : "null") +
                "}";
    }
}
