package com.example.lastproject.domain.user.dto;

import lombok.Getter;

@Getter
public class UserUpdateRequest {

    private String email;
    private String nickname;
    private String address;
}
