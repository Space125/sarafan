package com.example.sarafan.service;

import com.example.sarafan.domain.User;
import com.example.sarafan.repositories.UserDetailsRepositories;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author Ivan Kurilov on 10.06.2021
 */

@Service
public class ProfileService {
    private final UserDetailsRepositories userDetailsRepositories;

    public ProfileService(UserDetailsRepositories userDetailsRepositories) {
        this.userDetailsRepositories = userDetailsRepositories;
    }

    public User changeSubscription(User channel, User subscriber) {
        Set<User> subscribers = channel.getSubscribers();

        if (subscribers.contains(subscriber)) {
            subscribers.remove(subscriber);
        } else {
            subscribers.add(subscriber);
        }

        return userDetailsRepositories.save(channel);
    }

    public User getOne(String id) {
        return userDetailsRepositories.findById(id).orElse(null);
    }
}
