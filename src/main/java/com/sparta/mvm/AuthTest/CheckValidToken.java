package com.sparta.mvm.AuthTest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckValidToken {
    private boolean isValidToken;
    private boolean isExpiredToken;
    private boolean isValidRefreshToken;
    private boolean isExpiredRefreshToken;

    public CheckValidToken(){
        isValidToken = true;
        isExpiredToken = false;
        isValidRefreshToken = true;
        isExpiredRefreshToken = false;
    }


}
