package com.sparta.mvm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class TestUser {
    private String loginId;
    private String password;
    private String refreshToken;

    public TestUser() {
        loginId = "testId";
        password = "testPassword";
    }
}
