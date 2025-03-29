package org.example.chattrilogy.controller;

import org.example.chattrilogy.domain.Chat;
import org.example.chattrilogy.domain.Message;
import org.example.chattrilogy.service.ChatService;
import org.example.chattrilogy.util.error.IdInvalidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public ResponseEntity<List<Chat>> fetchAllChat(){
        return ResponseEntity.ok(chatService.getAllChats());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Chat>> fetchAllChatByUserId(@PathVariable("userId") int id) throws IdInvalidException {
        List<Chat> chats = chatService.getChatsByUserId(id);
        return ResponseEntity.ok(chats);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chat> fetchChatById(@PathVariable("id") int id) throws IdInvalidException {
        Chat chat = chatService.fetchChatById(id);
        return ResponseEntity.ok(chat);
    }

    @PutMapping("/add_message")
    public ResponseEntity<Chat> addMessage(
            @RequestParam int chatId,
            @RequestBody Message message) throws IdInvalidException {
        Chat chat = chatService.fetchChatById(chatId);
        chat.addMessage(message);
        System.out.println(message);
        chatService.updateChat(chat);
        return ResponseEntity.ok(chat);
    }

    @PutMapping("/send_message")
    public ResponseEntity<Message> sendMessage(
            @RequestParam int chatId,
            @RequestBody Message message) throws IdInvalidException {
        Chat chat = chatService.fetchChatById(chatId);
        chat.addMessage(message);
        chatService.updateChat(chat);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/between/{user1Id}/{user2Id}")
    public ResponseEntity<Integer> getChatIdByUser1IdAndUser2Id(
            @PathVariable("user1Id") int user1Id,
            @PathVariable("user2Id") int user2Id
    ) throws IdInvalidException {
        Integer chatId = chatService.fetchChatIdByUser1IdAndUser2Id(user1Id, user2Id);
        return ResponseEntity.ok(chatId);
    }

}
