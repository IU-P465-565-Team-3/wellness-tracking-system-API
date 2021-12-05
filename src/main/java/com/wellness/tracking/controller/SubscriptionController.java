package com.wellness.tracking.controller;

import com.wellness.tracking.model.*;
import com.wellness.tracking.repository.PublicUserRepository;
import com.wellness.tracking.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SubscriptionController {

    final SubscriptionRepository subscriptionRepository;
    final PublicUserRepository publicUserRepository;

    @PostMapping("/saveSubscribedProf")
    public ResponseEntity saveSubscriptions(@RequestBody PublicUser publisher) {
        try {
            UserSubscription userSubscription = new UserSubscription();

            PublicUser currentUser = getCurrentUser();
            PublicUser userByUsername = publicUserRepository.findUserByUsername(publisher.getUsername());
            if (userByUsername != null && currentUser != null) {
                userSubscription.setUser(currentUser);
                userSubscription.setPublisher(userByUsername);
                subscriptionRepository.save(userSubscription);
                return new ResponseEntity<>(null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getSubscribedProf")
    public ResponseEntity getSubscribers() {
        List<UserSubscription> subscribers = new ArrayList<>();
        try {
            PublicUser currentUser = getCurrentUser();
            subscriptionRepository.findByPublisher(currentUser).forEach((subscribers::add));
            return new ResponseEntity<>(subscribers, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public PublicUser getCurrentUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return publicUserRepository.findUserByUsername(username);
    }
}
