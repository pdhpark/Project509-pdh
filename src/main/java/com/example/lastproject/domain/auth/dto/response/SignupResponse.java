package com.example.lastproject.domain.auth.dto.response;

import com.example.lastproject.common.enums.CustomMessage;
import lombok.Getter;

@Getter
public class SignupResponse {

    private final String msg;

    public SignupResponse(String nickname) {
        this.msg = nickname + CustomMessage.SIGNUP_SUCCESS.getMessage();
    }

}