package com.example.lastproject.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    /*
    ex)
    예외명(대문자+'_') + (HttpStatus.오류코드, "메세지")
    STORE_FORBIDDEN(HttpStatus.FORBIDDEN,"사장님 권한을 가진 사용자만 가게를 생성할 수 있습니다."),
    STORE_BAD_REQUEST(HttpStatus.BAD_REQUEST,"사장님은 최대 3개의 가게까지만 운영할 수 있습니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 가게를 찾을 수 없습니다."),


    throw new CustomException(ErrorCode.STORE_FORBIDDEN);
    */

    // Token ErrorCode
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "조회 실패 : %s"),







    // 아래 코드 위에 ErrorCode 작성
    NOT_FOUND(HttpStatus.NOT_FOUND, "찾지못했습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message){
        this.status = httpStatus;
        this.message = message;
    }

    /**
     * 에러 메시지를 커스텀하는 메서드
     * @param detail 커스텀할 디테일한 메시지 파라미터
     * @return 커스텀된 에러 메시지 문자열
     */
    public String customMessage(String detail) {
        return String.format(message, detail);
    }
}
