package com.example.lastproject.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    /**
     * 선언 예시) <br />
     * 오류 코드는 HttpStatus ENUM 클래스 참고  <br />
     * 1) 예외명(대문자+'_') + (HttpStatus.오류코드, "메세지") - only 에러 메시지  <br />
     * 2) 예외명(대문자+'_') + (HttpStatus.오류코드, "에러 유형 : %s") - 유형 + 에러 메시지  <br />
     * STORE_FORBIDDEN(HttpStatus.FORBIDDEN,"사장님 권한을 가진 사용자만 가게를 생성할 수 있습니다."),  <br />
     * STORE_BAD_REQUEST(HttpStatus.BAD_REQUEST,"사장님은 최대 3개의 가게까지만 운영할 수 있습니다."),  <br />
     * STORE_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 가게를 찾을 수 없습니다."),  <br />
     * TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "조회 실패 : %s"),  <br />
     * USER_NOT_FOUND(HttpStatus.NOT_FOUND, "조회 실패 : %s");  <br />
     *  <br />
     * ======  <br />
     *  <br />
     * 서비스 내 사용 예시)  <br />
     * throw new CustomException(ErrorCode.STORE_FORBIDDEN);  <br />
     */


    // TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "조회 실패 : %s"),
    // Token ErrorCode
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "조회 실패 : %s"),

    // User ErrorCode
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자 조회 실패"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "검증 실패 : %s"),
    SIGNUP_ERROR(HttpStatus.BAD_REQUEST, "가입 실패 : %s"),
    SIGNIN_ERROR(HttpStatus.BAD_REQUEST, "로그인 실패 : %s"),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "변경 실패 : %s"),

    // UserRole ErrorCode
    USERROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "조회 실패 : %s"),

    // UserPenalty ErrorCode


    // Party ErrorCode
    PARTY_NOT_FOUND(HttpStatus.NOT_FOUND, "파티를 찾을 수 없습니다."),

    // PartyMember ErrorCode


    // Market ErrorCode


    // Item ErrorCode


    // Notification ErrorCode


    // Chat ErrorCode

    // UserReview ErrorCode


    // UserPenalty ErrorCode


    // Party ErrorCode


    // PartyMember ErrorCode


    // Market ErrorCode
    MARKET_NOT_FOUND(HttpStatus.NOT_FOUND, "마켓 정보를 찾을 수 없습니다."),

    // Item ErrorCode
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "일치하는 품목을 찾을 수 없습니다."),

    // Notification ErrorCode


    // OpenApi ErrorCode
    API_CONNECTION_ERROR(HttpStatus.BAD_REQUEST, "API 요청이 잘못되었습니다."),
    API_KEY_NOT_FOUND(HttpStatus.NOT_FOUND, "API 인증키를 찾을 수 없습니다."),

    // Chat ErrorCode


    // 아래 코드 위에 ErrorCode 작성
    NOT_FOUND(HttpStatus.NOT_FOUND, "대상을 찾지 못했습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.status = httpStatus;
        this.message = message;
    }

    /**
     * 에러 메시지를 커스텀하는 메서드
     *
     * @param detail 커스텀할 디테일한 메시지 파라미터
     * @return 커스텀된 에러 메시지 문자열
     */
    public String customMessage(String detail) {
        return String.format(message, detail);
    }
}
