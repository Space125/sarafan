package com.example.sarafan.repositories;

import com.example.sarafan.domain.User;
import com.example.sarafan.domain.UserSubscription;
import com.example.sarafan.domain.UserSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Ivan Kurilov on 11.06.2021
 */
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, UserSubscriptionId> {
    List<UserSubscription> findBySubscriber(User user);

    List<UserSubscription> findByChannel(User channel);

    UserSubscription findByChannelAndSubscriber(User channel, User subscriber);
}
