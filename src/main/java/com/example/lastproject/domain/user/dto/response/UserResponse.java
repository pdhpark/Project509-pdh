package com.example.lastproject.domain.user.dto.response;

import lombok.Getter;

@Getter
public class UserResponse {

    private final String email;
    private final String profileImg = "";
    private final String nickname;
    private final String msg;

    public UserResponse(String email, String nickname, String msg) {

        this.email = email;
        this.nickname = nickname;
        this.msg = msg;

    }

}
