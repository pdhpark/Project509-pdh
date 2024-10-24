package com.example.lastproject.domain.auth.service;

import com.example.lastproject.common.CustomException;
import com.example.lastproject.common.ErrorCode;
import com.example.lastproject.config.JwtUtil;
import com.example.lastproject.domain.auth.dto.request.SigninRequest;
import com.example.lastproject.domain.auth.dto.request.SignupRequest;
import com.example.lastproject.domain.auth.dto.response.SignupResponse;
import com.example.lastproject.domain.auth.entity.AuthUser;
import com.example.lastproject.domain.user.entity.User;
import com.example.lastproject.domain.user.enums.UserRole;
import com.example.lastproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * 회원가입
     *
     * @param signupRequest 회원가입 시 필요한 json body ( 이메일, 비밀번호, 닉네임, 주소, 권한 )
     * @return responseDTO ( "${닉네임} 님의 회원가입이 완료되었습니다" )
     */
    @Transactional
    public SignupResponse signup(SignupRequest signupRequest) {

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new CustomException(ErrorCode.SIGNUP_ERROR, "이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        UserRole userRole = UserRole.of(signupRequest.getUserRole());

        User newUser = new User(
                signupRequest.getEmail(),
                encodedPassword,
                signupRequest.getNickname(),
                signupRequest.getAddress(),
                userRole
        );
        User savedUser = userRepository.save(newUser);

        return new SignupResponse(savedUser.getNickname());
    }

    /**
     * 로그인
     *
     * @param signinRequest 로그인 시 필요한 json body ( 이메일, 비밀번호 )
     * @return 로그인 후 발급되는 토큰 반환 ( "Bearer eyJ~" )
     */
    public String signin(SigninRequest signinRequest) {
        User user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.SIGNIN_ERROR, "가입되지 않은 유저입니다.")
        );


        // 로그인 시 이메일과 비밀번호가 일치하지 않을 경우 401을 반환합니다.
        if (!passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.SIGNIN_ERROR, "잘못된 비밀번호입니다.");
        }

        return jwtUtil.createToken(user.getId(), user.getEmail(), user.getUserRole());
    }

    /**
     * 회원 탈퇴
     *
     * @param authUser 로그인한 사용자
     */
    @Transactional
    public void withdrawal(@AuthenticationPrincipal AuthUser authUser) {
        User user = userRepository.findById(authUser.getUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다.")
        );
        user.toggleDelete();
    }
}