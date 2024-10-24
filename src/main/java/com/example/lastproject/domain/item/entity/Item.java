package com.example.lastproject.domain.item.entity;

import com.example.lastproject.domain.likeitem.entity.LikeItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private String productName;

    @OneToMany(mappedBy = "item")
    private List<LikeItem> likeItems = new ArrayList<>();
}
