package com.example.sarafan.controller;

import com.example.sarafan.domain.User;
import com.example.sarafan.domain.UserSubscription;
import com.example.sarafan.domain.Views;
import com.example.sarafan.service.ProfileService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ivan Kurilov on 10.06.2021
 */

@RestController
@RequestMapping("profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProfile.class)
    public User getOne(@PathVariable("id") User user) {
        return user;
    }

    @PostMapping("change-subscription/{channelId}")
    @JsonView(Views.FullProfile.class)
    public User changeSubscription(
            @PathVariable("channelId") User channel,
            @AuthenticationPrincipal User subscriber
    ) {
        if (subscriber.equals(channel)) {
            return channel;
        } else {
            return profileService.changeSubscription(channel, subscriber);
        }
    }

    @GetMapping("get-subscribers/{channelId}")
    @JsonView(Views.IdText.class)
    public List<UserSubscription> subscribers(@PathVariable("channelId") User channel) {
        return profileService.getSubscribers(channel);
    }

    @PostMapping("change-status/{subscriberId}")
    @JsonView(Views.IdText.class)
    public UserSubscription changeSubscriptionStatus(
            @AuthenticationPrincipal User channel,
            @PathVariable("subscriberId") User subscriber) {
        return profileService.changeSubscriptionStatus(channel, subscriber);
    }
}
