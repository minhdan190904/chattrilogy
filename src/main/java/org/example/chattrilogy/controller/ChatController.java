package org.example.chattrilogy.controller;

import org.example.chattrilogy.domain.Chat;
import org.example.chattrilogy.domain.Message;
import org.example.chattrilogy.service.ChatService;
import org.example.chattrilogy.util.error.IdInvalidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chats")
    public ResponseEntity<List<Chat>> fetchAllChat(){
        return ResponseEntity.ok(chatService.getAllChats());
    }

    @GetMapping("/all_chats/{userId}")
    public ResponseEntity<List<Chat>> fetchAllChatByUserId(@PathVariable("userId") int id) throws IdInvalidException {
        List<Chat> chats = chatService.getChatsByUserId(id);
        return ResponseEntity.ok(chats);
    }

    @GetMapping("/chats/{id}")
    public ResponseEntity<Chat> fetchChatById(@PathVariable("id") int id) throws IdInvalidException {
        Chat chat = chatService.fetchChatById(id);
        return ResponseEntity.ok(chat);
    }

    @PutMapping("/message")
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

}
