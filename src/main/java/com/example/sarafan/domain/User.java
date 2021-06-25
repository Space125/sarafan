package com.example.sarafan.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ivan Kurilov on 19.05.2021
 */

@Entity
@Table(name="users")
@Data
@ToString(of = {"id", "name"})
@EqualsAndHashCode(of = {"id"})
public class User implements Serializable {
    @Id
    @JsonView(Views.IdText.class)
    private String id;
    @JsonView(Views.IdText.class)
    private String name;
    @JsonView(Views.IdText.class)
    private String userpic;

    private String email;

    @JsonView(Views.FullProfile.class)
    private String gender;
    @JsonView(Views.FullProfile.class)
    private String locale;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.FullProfile.class)
    private LocalDateTime lastVisit;


    @JsonView(Views.FullProfile.class)
    @OneToMany(mappedBy = "subscriber", orphanRemoval = true)
    private Set<UserSubscription> subscriptions = new HashSet<>();

    @JsonView(Views.FullProfile.class)
    @OneToMany(mappedBy = "channel", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<UserSubscription> subscribers = new HashSet<>();
}
