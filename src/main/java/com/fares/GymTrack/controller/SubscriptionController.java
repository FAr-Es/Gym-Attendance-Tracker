package com.fares.GymTrack.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fares.GymTrack.repository.MemberRepository;
import com.fares.GymTrack.repository.MembershipPlanRepository;
import com.fares.GymTrack.repository.SubscriptionRepository;
import com.fares.GymTrack.services.MembershipStatus;
import com.fares.GymTrack.services.MembershipStatusCalculator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fares.GymTrack.entity.Member;
import com.fares.GymTrack.entity.MembershipPlan;
import com.fares.GymTrack.entity.Subscription;

@RestController
public class SubscriptionController {

    private final SubscriptionRepository repository;
    private final MembershipPlanRepository membershipPlanRepository;
    private final MemberRepository memberRepository;

    public SubscriptionController(SubscriptionRepository repository,
            MembershipPlanRepository membershipPlanRepository,
            MemberRepository memberRepository) {
        this.repository = repository;
        this.membershipPlanRepository = membershipPlanRepository;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/api/v1/subscriptions")
    public List<Subscription> getAllSubscriptions() {
        return repository.findAll();
    }

    @GetMapping("/api/v1/subscriptions/{id}")
    public Subscription getSubscriptionById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscription not found"));
    }

    @GetMapping("/api/v1/subscriptions/{id}/status")
    public MembershipStatus getSubscriptionStatus(@PathVariable Long id) {

        Subscription subscription = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscription not found"));

        return MembershipStatusCalculator.calculateStatus(
                subscription.getStartDate(),
                subscription.getEndDate());
    }

    @PostMapping("/api/v1/subscriptions")
    public Subscription createSubscription(@RequestBody Subscription subscription) {

        Long memberId = subscription.getMember().getId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));

        Long planId = subscription.getMembershipPlan().getId();
        MembershipPlan plan = membershipPlanRepository.findById(planId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan not found"));

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(plan.getDurationDays());

        subscription.setStartDate(startDate);
        subscription.setEndDate(endDate);
        subscription.setPricePaid(plan.getPrice());
        subscription.setStatus("ACTIVE");
        subscription.setCreatedAt(LocalDateTime.now());

        return repository.save(subscription);
    }
}