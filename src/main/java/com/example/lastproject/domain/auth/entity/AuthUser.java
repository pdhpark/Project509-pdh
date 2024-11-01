package com.example.lastproject.domain.auth.entity;

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

    private final Long userId;
    private final String email;
    private final UserRole role;

    public AuthUser(Long userId, String email, UserRole role) {
        this.userId = userId;
        this.email = email;
        this.role = role; // UserRole로 변경
    }

    // 필요에 따라 authorities를 추가할 수도 있습니다.
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name())); // role을 권한으로 변환
    }
}
