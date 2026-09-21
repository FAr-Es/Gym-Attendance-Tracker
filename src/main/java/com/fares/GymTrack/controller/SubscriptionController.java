package com.fares.GymTrack.controller;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fares.GymTrack.repository.SubscriptionRepository;
import java.util.List;
import com.fares.GymTrack.entity.Subscription;

@RestController 
public class SubscriptionController {
    private final SubscriptionRepository repository;

    public SubscriptionController(SubscriptionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/v1/subscriptions")
    public List<Subscription> getAllSubscriptions() {
        return repository.findAll();
    }

    @GetMapping("/api/v1/subscriptions/{id}")
    public Subscription getsubscriptionById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "subscription not found")); 
            // return 404 if not found. badal el null 
    }
}
