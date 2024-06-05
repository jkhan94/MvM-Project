package com.sparta.mvm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileRequestDto {
    @NotBlank
    private String name;

    private String lineIntro;

    @NotNull
    private String currentPassword; // 현재 비밀번호

    @NotNull
    private String changedPassword; // 바꾼 비밀번호

    public void setChangedPassword(String changedPassword) {
        this.changedPassword = changedPassword;
    }
}
