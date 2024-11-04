package com.example.lastproject.domain.auth.service;

import com.example.lastproject.domain.auth.dto.request.SigninRequest;
import com.example.lastproject.domain.auth.dto.request.SignupRequest;
import com.example.lastproject.domain.auth.dto.response.SignupResponse;
import com.example.lastproject.domain.auth.dto.response.WithdrawalResponse;
import com.example.lastproject.common.dto.AuthUser;

public interface AuthService {

    SignupResponse signup(SignupRequest signupRequest);

    String signin(SigninRequest signinRequest);

    WithdrawalResponse withdrawal(AuthUser authUser);

}
