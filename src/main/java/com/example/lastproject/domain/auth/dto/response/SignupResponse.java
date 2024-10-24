package com.example.lastproject.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class SignupResponse {

    private final String msg;

    public SignupResponse(String nickname) {
        this.msg = nickname + "님의 가입이 성공적으로 완료되었습니다.";
    }
}