package com.example.lastproject.domain.item.repository;

import com.example.lastproject.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
