
package com.fares.GymTrack.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fares.GymTrack.entity.CheckIns;


public interface CheckInsRepository extends JpaRepository<CheckIns, Long> {
    List<CheckIns> findByCheckInDate(LocalDate date);
}