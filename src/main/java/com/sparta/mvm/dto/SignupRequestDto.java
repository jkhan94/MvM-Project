package com.sparta.mvm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank(message = "ID는 공백일 수 없습니다.")
    @Size(min = 10, max = 20, message = "아이디는 최소 10자 이상, 20자 이하로 작성해주세요.")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "아이디는 대소문자 포함 영문 + 숫자만을 허용합니다.")
    private String username;
    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    @Size(min = 10, message = "비밀번호는 최소 10자 이상 작성해주세요.")
    @Pattern(regexp = "^(?=.*?[A-Za-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{10,}$",
            message = "대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야합니다. ")
    private String password;
    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private String name;
    private String lineIntro;
    @Email
    @NotBlank(message = "email은 공백일 수 없습니다.")
    private String email;
}
