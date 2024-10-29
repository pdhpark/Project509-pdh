package com.example.lastproject.common.enums;

import org.springframework.http.HttpStatus;

public enum CustomMessage {

    NOT_FOUND(HttpStatus.NOT_FOUND, "대상을 찾지 못했습니다."),

    // User Success
    USER_FOUND(HttpStatus.OK, "님이 조회되었습니다."),
    CHANGE_PW_SUCCESS(HttpStatus.OK, "님의 비밀번호 변경이 완료되었습니다."),
    CHANGE_DETAIL_SUCCESS(HttpStatus.OK, "님의 정보 변경이 완료되었습니다."),



    // UserRole Success



    // UserPenalty Success



    // Party Success



    // PartyMember Success



    // Market Success



    // Item Success
    ON_SUCCESS(HttpStatus.OK, "Success");

    
    // LikeItem Success
    
    
    
    // Notification Success



    // Chat Success
    
    
    


    private final HttpStatus status;
    private final String message;

    CustomMessage(HttpStatus httpStatus, String message) {
        this.status = httpStatus;
        this.message = message;
    }
}
