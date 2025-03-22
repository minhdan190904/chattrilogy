package org.example.chattrilogy.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    private String message;

    private String sendingTime;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    private boolean isSeen;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    @JsonBackReference
    private Chat chat;

    public Message(User sender, String message, MessageType messageType, boolean isSeen) {
        this.sender = sender;
        this.message = message;
        this.isSeen = isSeen;
        this.messageType = messageType;
        this.sendingTime = "";
    }

    public Message() {
        this.sender = new User();
        this.message = "";
        this.isSeen = false;
        this.messageType = MessageType.TEXT;
        this.sendingTime = "";
    }

    public int getId() {
        return id;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public void setSendingTime(String sendingTime) {
        this.sendingTime = sendingTime;
    }

    public String getMessage() {
        return message;
    }

    public String getSendingTime() {
        return sendingTime;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
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


    public boolean getSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }
}
