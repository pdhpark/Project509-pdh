package com.example.lastproject.common;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private ErrorCode errorCode;

    // 1) 예외명(대문자+'_') + (HttpStatus.오류코드, "메세지") - only 에러 메시지
    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    // 2) 예외명(대문자+'_') + (HttpStatus.오류코드, "에러 유형 : %s") - 유형 + 에러 메시지
    public CustomException(ErrorCode errorCode, String detail) {
        super(String.format(errorCode.getMessage(), detail)); // 메시지 포맷팅
        this.errorCode = errorCode;
    }
}