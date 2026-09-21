
package com.fares.GymTrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fares.GymTrack.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}