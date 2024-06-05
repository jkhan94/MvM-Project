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

    @NotBlank
    private String currentPassword; // 이전 비밀번호

    @NotNull
    private String changedPassword; // 바꾼 비밀번호

    public void setChangedPassword(String changedPassword) {
        this.changedPassword = changedPassword;
    }

    public void setLineIntro(String lineIntro) {
        this.lineIntro = lineIntro;
    }
}
