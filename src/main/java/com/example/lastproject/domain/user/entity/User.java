package com.example.lastproject.domain.user.entity;


import com.example.lastproject.common.Timestamped;
import com.example.lastproject.domain.auth.entity.AuthUser;
import com.example.lastproject.domain.likeitem.entity.LikeItem;
import com.example.lastproject.domain.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "like_item_id")
    private List<LikeItem> likeItems;

    public User(String email, String password, UserRole userRole) {
        this.email = email;
        this.password = password;
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

//    public void updateRole(UserRole userRole) {
//        this.userRole = userRole;
//    }

    public void toggleDelete() {
        this.isDeleted = true;
    }
}