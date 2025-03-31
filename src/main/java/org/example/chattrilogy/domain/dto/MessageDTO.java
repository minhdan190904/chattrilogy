package org.example.chattrilogy.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.chattrilogy.domain.Message;

@Setter
@Getter
public class MessageDTO {
    private Message message;
    private Integer receiverId;
    private Integer chatId;

    public MessageDTO(Message message, Integer receiverId, Integer chatId) {
        this.message = message;
        this.receiverId = receiverId;
        this.chatId = chatId;
    }

}
