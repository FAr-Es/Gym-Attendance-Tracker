package com.fares.GymTrack.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fares.GymTrack.entity.CheckIns;
import com.fares.GymTrack.entity.Member;
import com.fares.GymTrack.entity.Subscription;
import com.fares.GymTrack.repository.CheckInsRepository;
import com.fares.GymTrack.repository.MemberRepository;
import com.fares.GymTrack.repository.SubscriptionRepository;

@RestController
public class CheckInsController {

    private final CheckInsRepository repository;
    private final MemberRepository memberRepository;
    private final SubscriptionRepository subscriptionRepository;

    public CheckInsController(CheckInsRepository repository, MemberRepository memberRepository,
            SubscriptionRepository subscriptionRepository) {
        this.repository = repository;
        this.memberRepository = memberRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @GetMapping("/api/v1/check-ins")
    public List<CheckIns> getAllCheckIns() {
        return repository.findAll();
    }

    @GetMapping("/api/v1/check-ins/{id}")
    public CheckIns getCheckInById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Check-in not found"));
    }

    @PostMapping("/api/v1/check-ins")
    public CheckIns createCheckIn(@RequestBody CheckIns checkIn) {
        Long memberId = checkIn.getMember().getId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));

        Long subscriptionId = checkIn.getSubscription().getId();
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscription not found"));
        checkIn.setCheckInDate(LocalDate.now());
        checkIn.setCheckInTime(LocalDateTime.now());

        try {
            return repository.save(checkIn);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Member already checked in today");
        }
    }
}