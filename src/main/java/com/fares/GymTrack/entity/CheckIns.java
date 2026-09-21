package com.fares.GymTrack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity(name = "check_ins") 
public class CheckIns {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
}
