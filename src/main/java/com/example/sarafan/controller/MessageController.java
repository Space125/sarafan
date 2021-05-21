package com.example.sarafan.controller;

import com.example.sarafan.domain.Message;
import com.example.sarafan.domain.Views;
import com.example.sarafan.repositories.MessageRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Ivan Kurilov on 14.05.2021
 */

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageRepository messageRepository;


    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping
    @JsonView(Views.IdText.class)
    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getOne(@PathVariable("id") Message message) {
        return messageRepository.getOne(message.getId());
    }

    @PostMapping
    public Message create(@RequestBody Message message) {
        message.setCreationDate(LocalDateTime.now());
        return messageRepository.save(message);
    }

    @PutMapping("{id}")
    public Message update(
            @PathVariable("id") Message messageIdFromDb,
            @RequestBody Message message) {
        BeanUtils.copyProperties(message, messageIdFromDb, "id");
        return messageRepository.save(messageIdFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageRepository.delete(message);
    }
}
