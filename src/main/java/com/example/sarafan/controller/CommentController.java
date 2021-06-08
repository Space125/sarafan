package com.example.sarafan.controller;

import com.example.sarafan.domain.Comment;
import com.example.sarafan.domain.User;
import com.example.sarafan.domain.Views;
import com.example.sarafan.service.CommentService;
import lombok.AllArgsConstructor;
import org.codehaus.jackson.map.annotate.JsonView;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ivan Kurilov on 07.06.2021
 */

@RestController
@AllArgsConstructor
@RequestMapping("comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @JsonView(Views.FullComment.class)
    public Comment create(
            @RequestBody Comment comment,
            @AuthenticationPrincipal User user
    ){
        return commentService.create(comment, user);
    }
}
