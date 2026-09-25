package com.fares.GymTrack.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fares.GymTrack.entity.Member;
import com.fares.GymTrack.repository.MemberRepository;

@RestController
public class MemberController {

    private final MemberRepository repository;

    public MemberController(MemberRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/v1/members")
    public List<Member> getAllMembers() {
        return repository.findAll();
    }

    @GetMapping("/api/v1/members/{id}")
    public Member getMemberById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));
        // return 404 if not found. badal el null
    }

    @PostMapping("/api/v1/members")
    public Member createMember(@RequestBody Member member) {
        member.setCreatedAt(LocalDateTime.now());

        try {
            return repository.save(member);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already exists");
        }
    }
}