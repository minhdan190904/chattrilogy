package org.example.chattrilogy.service;

import org.example.chattrilogy.domain.Chat;
import org.example.chattrilogy.domain.RequestStatus;
import org.example.chattrilogy.domain.User;
import org.example.chattrilogy.domain.dto.FriendRequestDTO;
import org.example.chattrilogy.repository.ChatRepository;
import org.example.chattrilogy.repository.UserRepository;
import org.example.chattrilogy.util.error.IdInvalidException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserService userService;

    public ChatService(ChatRepository chatRepository, UserService userService) {
        this.chatRepository = chatRepository;
        this.userService = userService;
    }

    public List<Chat> getAllChats(){
        return this.chatRepository.findAll();
    }

    public List<Chat> getChatsByUserId(int userId) throws IdInvalidException {
        User currentUser = userService.fetchUserById(userId);

        return chatRepository.findByUser1IdOrUser2Id(userId, userId).stream()
                .filter(chat -> chat.getOtherUser(currentUser) != null)
                .collect(Collectors.toList());
    }



    public Chat fetchChatById(int idChat) throws IdInvalidException {
        Chat chat = chatRepository.findById(idChat).orElse(null);
        if(chat == null){
            throw new IdInvalidException("Not exist user with id: " + idChat);
        }
        return chat;
    }

    public void updateChat(Chat chat) throws IdInvalidException {
        Chat currentChat = this.fetchChatById(chat.getId());
        if (currentChat != null) {
            this.chatRepository.save(chat);
        }
    }

}
