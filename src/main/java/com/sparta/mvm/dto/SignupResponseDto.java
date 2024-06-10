package com.sparta.mvm.dto;

import com.sparta.mvm.entity.User;
import com.sparta.mvm.entity.UserStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SignupResponseDto {

    String username;
    String name;
    String lineIntro;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
    UserStatusEnum userStatusEnum;

    public SignupResponseDto(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.lineIntro = user.getLineIntro();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
        this.userStatusEnum = user.getUserStatus();
    }
}
