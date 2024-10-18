package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    public MessageService(MessageRepository mRepository) {
            messageRepository = mRepository;
    }

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageByID(int id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent())
            return message.get();
        return null;
    }

    public void deleteMessageByID(int id) {
        messageRepository.deleteById(id);
    }

    public Message updateMessageByID(int id, String text) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isEmpty())
            return null;
        Message updatedMessage = message.get();
        updatedMessage.setMessageText(text);
        return messageRepository.save(updatedMessage);
    }

    public List<Message> getAllMessagesByUser(int accountID) {
        return messageRepository.findAllByPostedBy(accountID);
    }
}
