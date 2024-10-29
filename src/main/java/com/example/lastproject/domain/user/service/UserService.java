package com.example.lastproject.domain.user.service;

import com.example.lastproject.common.CustomException;
import com.example.lastproject.common.enums.CustomMessage;
import com.example.lastproject.common.enums.ErrorCode;
import com.example.lastproject.domain.penalty.entity.Penalty;
import com.example.lastproject.domain.penalty.repository.PenaltyRepository;
import com.example.lastproject.domain.user.dto.request.UserChangePasswordRequest;
import com.example.lastproject.domain.user.dto.request.UserUpdateRequest;
import com.example.lastproject.domain.user.dto.response.UserResponse;
import com.example.lastproject.domain.user.entity.User;
import com.example.lastproject.domain.user.enums.UserStatus;
import com.example.lastproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PenaltyRepository penaltyRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자의 페널티 횟수에 따른 닉네임 표기
     *
     * @param nickname  사용자의 닉네임
     * @param penalties 사용자가 받은 페널티 내역
     * @return 해당하는 이모지 + 닉네임
     */
    private String getNicknameWithEmoji(String nickname, List<Penalty> penalties) {

        LocalDateTime lastPenaltyDate = penalties.stream()
                .map(Penalty::getCreatedAt)
                .max(LocalDateTime::compareTo)
                .orElse(null);

        String emoji;
        int penaltyCount = penalties.size();

        // 페널티 횟수가 3회 이상이라면
        if (penaltyCount >= 3) {
            // 마지막 페널티를 받은지 3개월이 지났다면
            if (lastPenaltyDate.plusMonths(3).isBefore(LocalDateTime.now())) {
                emoji = "⭐";
            } else {
                emoji = "👻";
            }
        } else {
            emoji = "⭐";
        }

        return emoji + nickname;
    }

    /**
     * 사용자 조회
     *
     * @param userId 조회할 사용자 id
     * @return response 객체 ( email, nickname, "_ 님이 조회되었습니다." )
     */
    public UserResponse getUser(long userId) {

        User findUser = userRepository.findById(userId)
                .orElseThrow(
                        () -> new CustomException(ErrorCode.USER_NOT_FOUND)
                );

        if (findUser.getUserStatus() == (UserStatus.DELETED)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        List<Penalty> penalties = penaltyRepository.findPenaltiesByUserId(findUser);

        String nicknameWithEmoji = getNicknameWithEmoji(findUser.getNickname(), penalties);

        return new UserResponse(findUser.getEmail(), nicknameWithEmoji, CustomMessage.USER_FOUND.getMessage());
    }

    /**
     * 비밀번호 변경
     *
     * @param userId  비밀번호 변경할 사용자 id
     * @param request 비밀번호 변경에 필요한 정보 ( 기존 비밀번호, 새 비밀번호 )
     * @return response 객체 ( email, nickname, "_ 님이 조회되었습니다." )
     */
    @Transactional
    public UserResponse changePassword(long userId, UserChangePasswordRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new CustomException(ErrorCode.USER_NOT_FOUND)
                );

        // 기존 비밀번호 확인
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.VALIDATION_ERROR);
        }

        // 새 비밀번호와 기존 비밀번호가 같은지 확인
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.VALIDATION_ERROR);
        }

        user.changePassword(passwordEncoder.encode(request.getNewPassword()));

        return new UserResponse(user.getEmail(), user.getNickname(), CustomMessage.CHANGE_PW_SUCCESS.getMessage());
    }

    /**
     * 사용자 정보 변경
     *
     * @param userId  사용자 정보를 변경할 사용자 id
     * @param request 사용자 정보 변경에 필요한 정보 ( email, nickname, address )
     * @return response 객체 ( email, nickname, "_ 님이 조회되었습니다." )
     */
    @Transactional
    public UserResponse updateUser(long userId, UserUpdateRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new CustomException(ErrorCode.USER_NOT_FOUND)
                );

        if (user.getUserStatus() == (UserStatus.DELETED)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        if (request.getNickname() == null && request.getAddress() == null) {
            throw new CustomException(ErrorCode.NO_CONTENTS);
        }

        user.update(request);
        userRepository.save(user);
        return new UserResponse(user.getEmail(), user.getNickname(), CustomMessage.CHANGE_DETAIL_SUCCESS.getMessage());
    }

}
