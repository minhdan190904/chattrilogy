package org.example.chattrilogy.domain.dto;
import org.example.chattrilogy.domain.RequestStatus;
import org.example.chattrilogy.domain.User;

public class FriendRequestDTO {

    private User sender;
    private User receiver;
    private RequestStatus requestStatus;

    public FriendRequestDTO(User sender, User receiver, RequestStatus requestStatus) {
        this.sender = sender;
        this.receiver = receiver;
        this.requestStatus = requestStatus;
    }

    public FriendRequestDTO() {
        this.sender = new User();
        this.receiver = new User();
        this.requestStatus = RequestStatus.REJECTED;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
