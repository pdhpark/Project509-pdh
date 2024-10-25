package com.example.lastproject.domain.user.entity;

import com.example.lastproject.common.Timestamped;
import com.example.lastproject.domain.auth.entity.AuthUser;
import com.example.lastproject.domain.likeitem.entity.LikeItem;
import com.example.lastproject.domain.user.dto.UserUpdateRequest;
import com.example.lastproject.domain.user.enums.UserRole;
import com.example.lastproject.domain.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.ACTIVATED;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public User(String email, String password, String nickname, String address, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.address = address;
        this.userRole = userRole;
    }

    private User(Long id, String email, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.userRole = userRole;
    }

    public static User fromAuthUser(AuthUser authUser) {
        return new User(
                authUser.getUserId(),
                authUser.getEmail(),
                UserRole.of(authUser.getAuthorities().stream().findFirst().get().getAuthority())
        );
    }

    public void changePassword(String password) {
        this.password = password;
    }

    // 탈퇴 처리 status ( ENUM ) 으로 로직 변경
    public void toggleDelete() {
        this.userStatus = UserStatus.DELETED;
    }

    public void update(UserUpdateRequest request) {
        this.email = request.getEmail();
        this.nickname = request.getNickname();
        this.address = request.getAddress();
    }
}