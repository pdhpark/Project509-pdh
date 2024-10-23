package com.example.lastproject.domain.item.entity;

import com.example.lastproject.domain.likeitem.entity.LikeItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Table
@Entity
@NoArgsConstructor
@Getter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private int price;

    @OneToMany(mappedBy = "like")
    private List<LikeItem> likeItems;
}
