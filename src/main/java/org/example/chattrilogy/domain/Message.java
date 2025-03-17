package org.example.chattrilogy.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    @JsonBackReference
    private Chat chat;

    public Message(User sender, String message, String sendingTime, MessageType messageType) {
        this.sender = sender;
        this.message = message;
        this.sendingTime = sendingTime;
        this.messageType = messageType;
    }

    public Message() {
        this.sender = new User();
        this.message = "";
        this.sendingTime = "";
        this.messageType = MessageType.TEXT;
    }

    public int getId() {
        return id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSendingTime(String sendingTime) {
        this.sendingTime = sendingTime;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
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
                ", sender=" + (sender != null ? sender.getId() : "null") +
                ", message='" + message + '\'' +
                ", sendingTime='" + sendingTime + '\'' +
                ", messageType=" + messageType +
                ", chat=" + (chat != null ? chat.getId() : "null") +
                '}';
    }
}
