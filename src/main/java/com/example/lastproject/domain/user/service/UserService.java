package com.example.lastproject.domain.user.service;

import com.example.lastproject.common.CustomException;
import com.example.lastproject.common.ErrorCode;
import com.example.lastproject.domain.user.dto.UserUpdateRequest;
import com.example.lastproject.domain.user.dto.request.UserChangePasswordRequest;
import com.example.lastproject.domain.user.dto.response.UserResponse;
import com.example.lastproject.domain.user.entity.User;
import com.example.lastproject.domain.user.enums.UserStatus;
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

        User findUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다.")
        );

        if (findUser.getUserStatus() == (UserStatus.DELETED)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND, "탈퇴된 유저입니다.");
        }

        return new UserResponse(findUser.getEmail(), findUser.getNickname(), "님이 조회되었습니다.");
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

        return new UserResponse(user.getEmail(), user.getNickname(), "님의 비밀번호 변경이 완료되었습니다.");
    }

    public UserResponse updateUser(Long userId, UserUpdateRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new CustomException(ErrorCode.USER_NOT_FOUND)
                );

        if (user.getUserStatus() == (UserStatus.DELETED)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        if (request.getEmail() == null && request.getNickname() == null && request.getAddress() == null ) {
            throw new CustomException(ErrorCode.INVALID_REQUEST, "변경할 정보가 없습니다.");
        }

        if (request.getEmail().equals(user.getEmail()) ) {

        }

        user.update(request);
        userRepository.save(user);
        return new UserResponse(user.getEmail(), user.getNickname(), "님의 정보 변경이 완료되었습니다.");
    }
}