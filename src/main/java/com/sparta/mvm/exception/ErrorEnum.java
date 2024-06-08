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
    NOT_VALID_TOKEN(400,"유효하지 않은 토큰입니다"),
    EXPIRED_REFRESH_TOKEN_VALUE(403,"리프레시 토큰이 만료되었습니다, 재로그인이 필요합니다"),
    EXPIRED_TOKEN_VALUE(403,"토큰이 만료되었습니다, 재발급이 필요합니다"),
    // profile
    USER_NOT_FOUND(400, "등록되지 않은 사용자입니다."),
    BAD_PASSWORD(400, "비밀번호를 확인해주세요"),
    SAME_PASSWORD(400, "입력이 기존과 동일합니다.");

    int statusCode;
    String msg; // 출력 메시지
}
