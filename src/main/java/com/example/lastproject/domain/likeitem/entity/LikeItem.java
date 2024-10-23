package com.example.lastproject.domain.likeitem.entity;

import com.example.lastproject.domain.item.entity.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "like_item")
@Getter
@NoArgsConstructor
public class LikeItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
}
