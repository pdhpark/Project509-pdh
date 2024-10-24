package com.example.lastproject.domain.penalty.repository;

import com.example.lastproject.domain.penalty.entity.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
}
