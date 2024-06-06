package com.sparta.mvm.dto;

import com.sparta.mvm.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileResponseDto {
    private String username;
    private String name;
    private String lineIntro;
    private String email;

    public ProfileResponseDto(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.lineIntro = user.getLineIntro();
        this.email = user.getEmail();
    }
}
