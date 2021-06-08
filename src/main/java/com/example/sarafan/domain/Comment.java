package com.example.sarafan.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author Ivan Kurilov on 07.06.2021
 */

@Entity
@Table
@Data
@EqualsAndHashCode(of = {"id"})
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
public class Comment {

    @Id
    @GeneratedValue
    @JsonView(Views.IdText.class)
    private Long id;

    @JsonView(Views.IdText.class)
    private String text;

    @ManyToOne
    @JoinColumn(name = "message_id")
    @JsonView(Views.IdText.class)
    private Message message;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @JsonView(Views.IdText.class)
    private User author;

}
