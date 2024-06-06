package com.sparta.mvm.AuthTest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@NoArgsConstructor
@Component
public class RefreshTokenDto {

    private String refreshTokenValue;
    private boolean isTokenValid;

    public RefreshTokenDto(String tokenValue) {
        this.refreshTokenValue = tokenValue;
    }

    public void setTokenValid(boolean valid) {
        this.isTokenValid = valid;
    }
}
