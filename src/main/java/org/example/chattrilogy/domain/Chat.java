package org.example.chattrilogy.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;

    @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy hh:mm:ss a", timezone = "GMT+07:00")
    private Date timeCreated;

    @Setter
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messageList = new ArrayList<>();

    public Chat() {}

    public Chat(User user1, User user2, Date timeCreated) {
        this.user1 = user1;
        this.user2 = user2;
        this.timeCreated = timeCreated;
    }

    public void addMessage(Message message) {
        message.setChat(this);
        messageList.add(message);
    }

    public String getChatTitle(User currentUser) {
        return currentUser.getId() == user1.getId() ? user2.getName() : user1.getName();
    }

    public String getChatImage(User currentUser) {
        return currentUser.getId() == user1.getId() ? user2.getProfileImageUrl() : user1.getProfileImageUrl();
    }


    public Message getLastMessage() {
        return messageList.isEmpty() ? null : messageList.get(messageList.size() - 1);
    }

    public User getOtherUser(User currentUser) {
        if (currentUser.getId() == user1.getId()) {
            return user2;
        } else {
            return user1;
        }
    }
}
