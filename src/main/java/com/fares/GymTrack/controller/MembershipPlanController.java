package com.fares.GymTrack.controller;

import com.fares.GymTrack.entity.MembershipPlan;
import com.fares.GymTrack.repository.MembershipPlanRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MembershipPlanController {

    private final MembershipPlanRepository repository;

    public MembershipPlanController(MembershipPlanRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/v1/plans")
    public List<MembershipPlan> getAllPlans() {
        return repository.findAll();
    }
}