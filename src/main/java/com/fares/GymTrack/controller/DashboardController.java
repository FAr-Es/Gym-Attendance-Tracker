package com.fares.GymTrack.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fares.GymTrack.entity.DashboardStats;
import com.fares.GymTrack.entity.Member;
import com.fares.GymTrack.entity.MemberStatusRow;
import com.fares.GymTrack.entity.MembershipPlan;
import com.fares.GymTrack.entity.Subscription;
import com.fares.GymTrack.repository.CheckInsRepository;
import com.fares.GymTrack.repository.MemberRepository;
import com.fares.GymTrack.repository.SubscriptionRepository;
import com.fares.GymTrack.services.MembershipStatus;
import com.fares.GymTrack.services.MembershipStatusCalculator;

@RestController
public class DashboardController {

    private final MemberRepository memberRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final CheckInsRepository checkInsRepository;

    public DashboardController(MemberRepository memberRepository, SubscriptionRepository subscriptionRepository, CheckInsRepository checkInsRepository) {
        this.memberRepository = memberRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.checkInsRepository = checkInsRepository;
    }

    @GetMapping("/api/v1/dashboard/members")
    public List<MemberStatusRow> getMembersWithStatus() {

        List<Member> members = memberRepository.findAll();
        List<MemberStatusRow> rows = new ArrayList<>();

        for (Member member : members) {

            List<Subscription> subscriptions = subscriptionRepository.findByMemberId(member.getId());
            Subscription latest = MembershipStatusCalculator.findMostRecentSubscription(subscriptions);

            MembershipStatus status;
            MembershipPlan plan;

            if (latest == null) {
                status = MembershipStatus.NONE;
                plan = null;
            } else {
                status = MembershipStatusCalculator.calculateStatus(latest.getStartDate(), latest.getEndDate());
                plan = latest.getMembershipPlan();
            }

            rows.add(new MemberStatusRow(member.getId(), member.getFullName(), member.getPhone(), status, plan));
        }

        return rows;
    }

    @GetMapping("/api/v1/dashboard/stats")
    public DashboardStats getDashboardStats() {

        List<Member> members = memberRepository.findAll();

        long activeCount = 0;
        long expiringSoonCount = 0;
        long expiredCount = 0;

        for (Member member : members) {
            List<Subscription> subscriptions = subscriptionRepository.findByMemberId(member.getId());
            Subscription latest = MembershipStatusCalculator.findMostRecentSubscription(subscriptions);

            if (latest == null) {
                continue; // no subscription at all — doesn't count toward any of the three
            }

            MembershipStatus status = MembershipStatusCalculator.calculateStatus(latest.getStartDate(),
                    latest.getEndDate());

            if (status == MembershipStatus.ACTIVE) {
                activeCount++;
            } else if (status == MembershipStatus.EXPIRING_SOON) {
                expiringSoonCount++;
            } else if (status == MembershipStatus.EXPIRED) {
                expiredCount++;
            }
        }

        long checkInsToday = checkInsRepository.findByCheckInDate(LocalDate.now()).size();

        DashboardStats stats = new DashboardStats();
        stats.setTotalMembers(members.size());
        stats.setActiveSubscriptions(activeCount);
        stats.setExpiringSoonSubscriptions(expiringSoonCount);
        stats.setExpiredSubscriptions(expiredCount);
        stats.setCheckInsToday(checkInsToday);

        return stats;
    }
}