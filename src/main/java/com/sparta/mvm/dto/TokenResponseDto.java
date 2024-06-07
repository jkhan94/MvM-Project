package com.sparta.mvm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenResponseDto {
    private String message;
    private int status;
    private String accessToken;
    private String refreshToken;
}
