package com.example.lastproject.domain.likeitem.repository;

import com.example.lastproject.domain.likeitem.entity.LikeItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeItemRepository extends JpaRepository<LikeItem, Long> {
}
