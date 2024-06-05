package com.sparta.mvm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ErrorEnum {
    // user
    // login
    // post
    // authorization
    // profile
    USER_NOT_FOUND(400,"등록되지 않은 사용자입니다."),
    NOT_VALID_ARGUMENTS(400,"입력값을 확인해주세요."),
    BAD_PASSWORD(400, "비밀번호를 확인해주세요"),
    SAME_PASSWORD(400, "입력이 기존과 동일합니다.");

    int statusCode;
    String msg; // 출력 메시지
}
