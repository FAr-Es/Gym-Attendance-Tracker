package com.fares.GymTrack.entity;

import com.fares.GymTrack.services.MembershipStatus;

public class MemberStatusRow {
     private Long id;
    private String fullName;
    private String phone;
    private MembershipStatus status;
    private MembershipPlan plan;

    public MemberStatusRow(Long id, String fullName, String phone, MembershipStatus status, MembershipPlan plan) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.status = status;
        this.plan = plan;
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public MembershipStatus getStatus() { return status; }
    public MembershipPlan getPlan() { return plan; }
}
