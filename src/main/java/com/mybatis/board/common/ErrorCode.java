package com.mybatis.board.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), "허용되지 않은 요청입니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "올바르지 않은 요청입니다."),
    ID_OR_PASSWORD_WRONG(HttpStatus.BAD_REQUEST.value(), "아이디 혹은 비밀번호가 일치하지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "내부 서버 오류입니다."),


    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "사용자를 찾을 수 없습니다."),
    MEMBER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST.value(), "이미 존재하는 사용자입니다."),
    PASSWORD_NOT_MATCHED(HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다."),
    ;


    private final int status;
    private final String message;
}
