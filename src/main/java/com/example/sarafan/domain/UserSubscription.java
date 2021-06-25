package com.example.sarafan.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

/**
 * @author Ivan Kurilov on 11.06.2021
 */

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id"})
@NoArgsConstructor
public class UserSubscription {
    @EmbeddedId
    @JsonIgnore
    private UserSubscriptionId id;

    @MapsId(value = "channelId")
    @ManyToOne
    @JsonView(Views.IdText.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    private User channel;

    @MapsId(value = "subscriberId")
    @ManyToOne
    @JsonView(Views.IdText.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    private User subscriber;

    @JsonView(Views.IdText.class)
    private boolean active;

    public UserSubscription(User channel, User subscriber) {
        this.channel = channel;
        this.subscriber = subscriber;
        this.id = new UserSubscriptionId(channel.getId(), subscriber.getId());
    }

}
