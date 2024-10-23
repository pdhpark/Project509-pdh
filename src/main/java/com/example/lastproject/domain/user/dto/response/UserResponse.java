package com.example.lastproject.domain.user.dto.response;

import lombok.Getter;

@Getter
public class UserResponse {

    private final Long id;
    private final String email;
    private final String changePwMsg;

    public UserResponse(Long id, String email, String changePwMsg) {
        this.id = id;
        this.email = email;
        this.changePwMsg = changePwMsg;
    }
}