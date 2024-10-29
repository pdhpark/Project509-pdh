package com.example.lastproject.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SigninRequest {

    @Email
    @NotBlank(message = "이메일은 공백일 수 없습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    private String password;

}
