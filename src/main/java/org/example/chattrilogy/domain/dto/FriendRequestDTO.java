package org.example.chattrilogy.domain.dto;
import lombok.Getter;
import lombok.Setter;
import org.example.chattrilogy.domain.RequestStatus;
import org.example.chattrilogy.domain.User;

@Setter
@Getter
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

}
