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
    BAD_POSTID(400, "게시글 ID를 찾을 수 없습니다."),
    // comment
    BAD_COMMENTID(400,"댓글 ID를 찾을 수 없습니다."),
    // authorization
    // profile
    USER_NOT_FOUND(400, "등록되지 않은 사용자입니다."),
    BAD_PASSWORD(400, "비밀번호를 확인해주세요"),
    SAME_PASSWORD(400, "입력이 기존과 동일합니다.");

    int statusCode;
    String msg; // 출력 메시지
}
