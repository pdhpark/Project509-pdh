package com.example.lastproject.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class SigninResponse {

    private final String msg;

    public SigninResponse() {
        this.msg = "로그인되었습니다.";
    }
}