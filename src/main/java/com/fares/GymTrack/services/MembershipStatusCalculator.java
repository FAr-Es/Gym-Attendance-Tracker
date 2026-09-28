package com.fares.GymTrack.services;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.fares.GymTrack.entity.Subscription;

public class MembershipStatusCalculator {


    public static MembershipStatus calculateStatus(LocalDate startDate, LocalDate endDate) {

        LocalDate today = LocalDate.now();

        boolean hasStarted = today.isEqual(startDate) || today.isAfter(startDate);
        boolean hasNotEnded = today.isEqual(endDate) || today.isBefore(endDate);

        if (today.isBefore(startDate)) {
            return MembershipStatus.UPCOMING;
        }

        if (hasStarted && hasNotEnded) {
            long daysUntilEnd = ChronoUnit.DAYS.between(today, endDate);

            if (daysUntilEnd <= 7) {
                return MembershipStatus.EXPIRING_SOON;
            }

            return MembershipStatus.ACTIVE;
        }

        return MembershipStatus.EXPIRED;
    }

    public static Subscription findMostRecentSubscription(List<Subscription> subscriptions) {

    Subscription mostRecent = null;

    for (Subscription subscription : subscriptions) {
        if (mostRecent == null || subscription.getStartDate().isAfter(mostRecent.getStartDate())) {
            mostRecent = subscription;
        }
    }

    return mostRecent;
}
}