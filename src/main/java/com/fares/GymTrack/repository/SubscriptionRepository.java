
package com.fares.GymTrack.repository;
import com.fares.GymTrack.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}