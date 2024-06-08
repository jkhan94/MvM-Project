package com.sparta.mvm.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum UserStatusEnum {
    USER_NORMAL("정상"),
    USER_RESIGN("탈퇴");

    String status;
}
