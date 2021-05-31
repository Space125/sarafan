package com.example.sarafan.controller;

import com.example.sarafan.domain.Message;
import com.example.sarafan.domain.Views;
import com.example.sarafan.dto.EventType;
import com.example.sarafan.dto.ObjectType;
import com.example.sarafan.repositories.MessageRepository;
import com.example.sarafan.util.WsSender;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author Ivan Kurilov on 14.05.2021
 */

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageRepository messageRepository;
    private final BiConsumer<EventType, Message> wsSender;

    public MessageController(MessageRepository messageRepository, WsSender wsSender) {
        this.messageRepository = messageRepository;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.IdText.class);
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

        Message createdMessage = messageRepository.save(message);
        wsSender.accept(EventType.CREATE, createdMessage);

        return createdMessage;
    }

    @PutMapping("{id}")
    public Message update(
            @PathVariable("id") Message messageIdFromDb,
            @RequestBody Message message) {

        BeanUtils.copyProperties(message, messageIdFromDb, "id");

        Message updatedMessage = messageRepository.save(messageIdFromDb);
        wsSender.accept(EventType.UPDATE, updatedMessage);

        return updatedMessage;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageRepository.delete(message);
        wsSender.accept(EventType.REMOVE, message);
    }

}
