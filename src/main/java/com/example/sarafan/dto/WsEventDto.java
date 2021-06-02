package com.example.sarafan.dto;

import com.example.sarafan.domain.Views;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Ivan Kurilov on 01.06.2021
 */
@Data
@AllArgsConstructor
@JsonView(Views.Id.class)
public class WsEventDto {
    private ObjectType objectType;
    private EventType eventType;

    @JsonRawValue
    private String body;
}
