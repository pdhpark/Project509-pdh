package com.example.lastproject.domain.auth.dto.response;

import com.example.lastproject.domain.user.enums.UserRole;
import lombok.Getter;

@Getter
public class SignupResponse {

    private final String msg;

    public SignupResponse(String email, String password, UserRole userRole) {
        this.msg = "성공적으로 가입이 완료되었습니다.";
    }
}