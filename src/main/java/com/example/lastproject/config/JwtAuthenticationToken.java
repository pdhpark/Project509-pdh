package com.example.lastproject.config;

import com.example.lastproject.domain.auth.entity.AuthUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    /*
    AbstractAuthenticationToken 을 상속받아 JWT 기반 인증을 처리하기 위해 설계된 커스텀 인증 토큰
    사용자 인증 정보를 담고 있는 객체(AuthUser)를 보유하고, 인증 상태를 관리
     */

    private final AuthUser authUser;

    /**
     * 사용자의 권한 정보를 전달
     *
     * @param authUser 인증된 사용자
     */
    public JwtAuthenticationToken(AuthUser authUser) {
        super(authUser.getAuthorities());
        this.authUser = authUser;
        setAuthenticated(true);
    }

    /**
     * AbstractAuthenticationToken 클래스는 Authentication 인터페이스를 구현하는 추상 클래스
     * 해당 메서드는 Authentication 인터페이스의 구현체 메서드
     * 다양한 인증 방식의 존재로 인해 ( 폼 로그인, JWT 인증, OAuth, ... ) 그 방식에 따른 반환값이 달라질 수 있음
     * 현재 JWT 토큰 인증 방식 사용으로 인해 서버에는 사용자 비밀번호를 보낼 필요가 없으며, 클라이언트가 보관하고 있는 토큰이 인증의 증거가 됨
     *
     * @return 자격 증명 정보
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * AbstractAuthenticationToken 클래스는 Authentication 인터페이스를 구현하는 추상 클래스
     * 해당 메서드는 Authentication 인터페이스의 구현체 메서드
     *
     * @return 현재 인증된 사용자 객체
     */
    @Override
    public Object getPrincipal() {
        return authUser;
    }

}
