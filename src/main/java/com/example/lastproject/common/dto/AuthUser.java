package com.example.lastproject.common.dto;

import com.example.lastproject.domain.user.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@Builder
public class AuthUser {

    /*
    AuthUser 는 인증, 인가된 후의 유저임을 알 수 있는 객체 -> 엔티티가 아닌 DTO
    엔티티로 생성됐을 때 JPA 에서 관리해야 할 필요성이 생기게 됨
     */

    private final Long userId;
    private final String email;
    private final UserRole role;

    public AuthUser(Long userId, String email, UserRole role) {
        this.userId = userId;
        this.email = email;
        this.role = role;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

}
