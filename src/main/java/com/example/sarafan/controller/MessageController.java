package com.example.sarafan.controller;

import com.example.sarafan.domain.Message;
import com.example.sarafan.domain.User;
import com.example.sarafan.domain.Views;
import com.example.sarafan.dto.MessagePageDto;
import com.example.sarafan.service.MessageService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author Ivan Kurilov on 14.05.2021
 */

@RestController
@RequestMapping("message")
public class MessageController {
    protected static final int MESSAGE_PER_PAGE = 3;

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @GetMapping
    @JsonView(Views.FullMessage.class)
    public MessagePageDto getMessages(
            @AuthenticationPrincipal User user,
            @PageableDefault(size = MESSAGE_PER_PAGE, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return messageService.findForUser(pageable, user);
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getOne(@PathVariable("id") Message message) {
        return messageService.getMessageById(message);
    }

    @PostMapping
    @JsonView(Views.FullMessage.class)
    public Message create(
            @RequestBody Message message,
            @AuthenticationPrincipal User user) throws IOException {
        return messageService.create(message, user);
    }

    @PutMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message update(
            @PathVariable("id") Message messageIdFromDb,
            @RequestBody Message message)
            throws IOException {
        return messageService.update(messageIdFromDb, message);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageService.delete(message);
    }


}
