package com.example.sarafan.service;

import com.example.sarafan.domain.Comment;
import com.example.sarafan.domain.User;
import com.example.sarafan.domain.Views;
import com.example.sarafan.dto.EventType;
import com.example.sarafan.dto.ObjectType;
import com.example.sarafan.repositories.CommentRepository;
import com.example.sarafan.util.WsSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.function.BiConsumer;

/**
 * @author Ivan Kurilov on 07.06.2021
 */

@Service
@RequestMapping("comment")
public class CommentService {
    private final CommentRepository commentRepository;

    private final BiConsumer<EventType, Comment> wsSender;

    public CommentService(CommentRepository commentRepository, WsSender wsSender) {
        this.commentRepository = commentRepository;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    public Comment create(Comment comment, User author){
        comment.setAuthor(author);
        Comment commentFromDb = commentRepository.save(comment);
        wsSender.accept(EventType.CREATE, commentFromDb);

        return commentFromDb;
    }
}
