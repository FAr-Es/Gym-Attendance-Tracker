package com.fares.GymTrack.services;
import java.time.LocalDate;

public class MembershipStatusCalculator {


    public static MembershipStatus calculateStatus(LocalDate startDate, LocalDate endDate) {

        LocalDate today = LocalDate.now();

        boolean hasStarted = today.isEqual(startDate) || today.isAfter(startDate);
        boolean hasNotEnded = today.isEqual(endDate) || today.isBefore(endDate);

        if (today.isBefore(startDate)) {
            return MembershipStatus.UPCOMING;
        }

        if (hasStarted && hasNotEnded) {
            long daysUntilEnd = today.until(endDate).getDays();

            if (daysUntilEnd <= 7) {
                return MembershipStatus.EXPIRING_SOON;
            }

            return MembershipStatus.ACTIVE;
        }

        return MembershipStatus.EXPIRED;
    }
}