package com.example.sarafan.dto;

import com.example.sarafan.domain.Message;
import com.example.sarafan.domain.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Ivan Kurilov on 09.06.2021
 */

@Data
@AllArgsConstructor
@JsonView(Views.FullMessage.class)
public class MessagePageDto {
    private List<Message> messages;
    private int currentPage;
    private int totalPages;
}
