package org.example.chattrilogy.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    private String timeSend;

    public FriendRequest() {
        this.requestStatus = RequestStatus.PENDING;
        this.timeSend = "";
    }

    public FriendRequest(User sender, User receiver, RequestStatus requestStatus, String timeSend) {
        this.sender = sender;
        this.receiver = receiver;
        this.requestStatus = requestStatus;
        this.timeSend = timeSend;
    }

}
