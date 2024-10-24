package com.example.lastproject.domain.notification.entity;

import com.example.lastproject.common.Timestamped;
import com.example.lastproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User receiver;

    @Column(nullable = false)
    private String message;

    private boolean isRead;

    public Notification(String message, User receiver) {
        this.message = message;
        this.receiver = receiver;
        this.isRead = false;
    }

    public void markAsRead() {
        this.isRead = true;
    }
}