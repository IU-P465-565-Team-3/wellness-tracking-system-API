package com.wellness.tracking.controller;

import com.wellness.tracking.enums.Role;
import com.wellness.tracking.model.*;
import com.wellness.tracking.repository.PublicUserRepository;
import com.wellness.tracking.repository.SubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class SubscriptionController {

    final SubscriptionRepository subscriptionRepository;
    final PublicUserRepository publicUserRepository;

    @GetMapping("/subscription")
    public ResponseEntity<List<Subscription>> getSubscriptions() {
        PublicUser currentUser = getCurrentPublicUser();
        if (!currentUser.getRole().equals(Role.PROFESSIONAL)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        List<Subscription> subscriptions = subscriptionRepository.findAllByProducer(currentUser);
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @GetMapping("/subscription/{producerId}")
    public ResponseEntity<List<Subscription>> getSubscriptionsById(@PathVariable Long producerId) {
        Optional<PublicUser> producer = publicUserRepository.findById(producerId);
            if (!producer.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        List<Subscription> subscriptions = subscriptionRepository.findAllByProducer(producer.get());
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @GetMapping("/subscription/{producerId}/count")
    public ResponseEntity<Long> getSubscriptionCountById(@PathVariable Long producerId) {
        Optional<PublicUser> producer = publicUserRepository.findById(producerId);
        if (!producer.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Long subscriptionCount = subscriptionRepository.countAllByProducer(producer.get());
        return new ResponseEntity<>(subscriptionCount, HttpStatus.OK);
    }

    @PostMapping("/subscription/{producerId}")
    public ResponseEntity<String> createSubscription(@PathVariable Long producerId) {
        if (producerId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        PublicUser consumer = getCurrentPublicUser();
        Optional<PublicUser> producer = publicUserRepository.findById(producerId);
        if (!producer.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Subscription subscription = new Subscription();
        subscription.setConsumer(consumer);
        subscription.setProducer(producer.get());
        subscriptionRepository.save(subscription);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    public PublicUser getCurrentPublicUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return publicUserRepository.findUserByUsername(username);
    }
}
