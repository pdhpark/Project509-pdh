package com.example.lastproject.domain.auth.controller;

import com.example.lastproject.domain.auth.dto.request.SigninRequest;
import com.example.lastproject.domain.auth.dto.request.SignupRequest;
import com.example.lastproject.domain.auth.dto.response.SigninResponse;
import com.example.lastproject.domain.auth.dto.response.SignupResponse;
import com.example.lastproject.domain.auth.entity.AuthUser;
import com.example.lastproject.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 회원가입
     *
     * @param signupRequest 회원가입 시 필요한 body ( 이메일, 비밀번호, 닉네임, 주소, 사용자 권한 )
     * @return responseDTO ( _ 님의 가입이 성공적으로 완료되었습니다
     */
    @PostMapping("/signup")
    public SignupResponse signup(@Valid @RequestBody SignupRequest signupRequest) {
        return authService.signup(signupRequest);
    }

    @PostMapping("/signin")
    public ResponseEntity<SigninResponse> signin(@Valid @RequestBody SigninRequest signinRequest) {
        String token = authService.signin(signinRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        SigninResponse response = new SigninResponse();
        return ResponseEntity.ok()
                .headers(headers)
                .body(response);
    }

    @GetMapping("/withdrawal")
    public ResponseEntity<String> withdrawal(@AuthenticationPrincipal AuthUser authUser) {
        authService.withdrawal(authUser);
        return ResponseEntity.accepted().
                body("탈퇴되었습니다.");
    }
}