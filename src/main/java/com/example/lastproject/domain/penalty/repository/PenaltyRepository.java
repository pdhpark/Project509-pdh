package com.example.lastproject.domain.penalty.repository;

import com.example.lastproject.domain.penalty.entity.Penalty;
import com.example.lastproject.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {

    List<Penalty> findPenaltiesByUserId(User user);

}
