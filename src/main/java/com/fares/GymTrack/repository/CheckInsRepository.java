
package com.fares.GymTrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fares.GymTrack.entity.CheckIns;


public interface CheckInsRepository extends JpaRepository<CheckIns, Long> {
}