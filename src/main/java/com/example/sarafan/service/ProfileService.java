package com.example.sarafan.service;

import com.example.sarafan.domain.User;
import com.example.sarafan.domain.UserSubscription;
import com.example.sarafan.repositories.UserDetailsRepositories;
import com.example.sarafan.repositories.UserSubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ivan Kurilov on 10.06.2021
 */

@Service
public class ProfileService {
    private final UserDetailsRepositories userDetailsRepositories;
    private final UserSubscriptionRepository userSubscriptionRepository;

    public ProfileService(
            UserDetailsRepositories userDetailsRepositories,
            UserSubscriptionRepository userSubscriptionRepository) {
        this.userDetailsRepositories = userDetailsRepositories;
        this.userSubscriptionRepository = userSubscriptionRepository;
    }

    public User changeSubscription(User channel, User subscriber) {
        List<UserSubscription> subscriptions = channel.getSubscribers()
                .stream()
                .filter(subscription -> subscription.getSubscriber().equals(subscriber))
                .collect(Collectors.toList());

        if (subscriptions.isEmpty()) {
            UserSubscription subscription = new UserSubscription(channel, subscriber);
            channel.getSubscribers().add(subscription);
        } else {
            channel.getSubscribers().removeAll(subscriptions);
        }

        return userDetailsRepositories.save(channel);
    }

    public User getOne(String id) {
        return userDetailsRepositories.findById(id).orElse(null);
    }

    public List<UserSubscription> getSubscribers(User channel) {
        return userSubscriptionRepository.findByChannel(channel);
    }

    public UserSubscription changeSubscriptionStatus(User channel, User subscriber) {
        UserSubscription subscription = userSubscriptionRepository.findByChannelAndSubscriber(channel, subscriber);
        subscription.setActive(!subscription.isActive());

        return userSubscriptionRepository.save(subscription);
    }
}
