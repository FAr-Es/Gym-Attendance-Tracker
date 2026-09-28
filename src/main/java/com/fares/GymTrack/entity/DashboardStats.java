package com.fares.GymTrack.entity;

public class DashboardStats {
    private long totalMembers;
    private long activeSubscriptions;
    private long expiringSoonSubscriptions;
    private long expiredSubscriptions;
    private long checkInsToday;

    public long getTotalMembers() { return totalMembers; }
    public void setTotalMembers(long totalMembers) { this.totalMembers = totalMembers; }

    public long getActiveSubscriptions() { return activeSubscriptions; }
    public void setActiveSubscriptions(long activeSubscriptions) { this.activeSubscriptions = activeSubscriptions; }

    public long getExpiringSoonSubscriptions() { return expiringSoonSubscriptions; }
    public void setExpiringSoonSubscriptions(long expiringSoonSubscriptions) { this.expiringSoonSubscriptions = expiringSoonSubscriptions; }

    public long getExpiredSubscriptions() { return expiredSubscriptions; }
    public void setExpiredSubscriptions(long expiredSubscriptions) { this.expiredSubscriptions = expiredSubscriptions; }

    public long getCheckInsToday() { return checkInsToday; }
    public void setCheckInsToday(long checkInsToday) { this.checkInsToday = checkInsToday; }
}