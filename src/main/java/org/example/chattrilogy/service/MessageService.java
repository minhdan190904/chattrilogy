package org.example.chattrilogy.service;

import org.example.chattrilogy.domain.Message;
import org.example.chattrilogy.domain.User;
import org.example.chattrilogy.repository.MessageRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void handleCreateMessage(Message message) {
        this.messageRepository.save(message);
    }
}
