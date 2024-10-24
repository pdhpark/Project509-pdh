package com.example.lastproject.domain.user.service;

import com.example.lastproject.common.CustomException;
import com.example.lastproject.common.ErrorCode;
import com.example.lastproject.domain.user.dto.request.UserChangePasswordRequest;
import com.example.lastproject.domain.user.dto.response.UserResponse;
import com.example.lastproject.domain.user.entity.User;
import com.example.lastproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse getUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다.")
        );
        return new UserResponse(user.getId(), user.getEmail(), "사용자가 조회되었습니다.");
    }

    @Transactional
    public UserResponse changePassword(long userId, UserChangePasswordRequest userChangePasswordRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new CustomException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다.")
                );

        if (!passwordEncoder.matches(userChangePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.VALIDATION_ERROR, "입력한 기존 비밀번호가 올바르지 않습니다.");
        }

        if (passwordEncoder.matches(userChangePasswordRequest.getNewPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.VALIDATION_ERROR, "새 비밀번호는 기존 비밀번호와 같을 수 없습니다.");
        }

        user.changePassword(passwordEncoder.encode(userChangePasswordRequest.getNewPassword()));

        return new UserResponse(user.getId(), user.getEmail(), "비밀번호 변경이 완료되었습니다.");
    }
}