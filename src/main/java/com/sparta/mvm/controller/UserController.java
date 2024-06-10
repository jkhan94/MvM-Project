package com.sparta.mvm.controller;

import com.sparta.mvm.dto.ResignDto;
import com.sparta.mvm.dto.SignupRequestDto;
import com.sparta.mvm.dto.SignupResponseDto;
import com.sparta.mvm.exception.CommonResponse;
import com.sparta.mvm.security.UserDetailsImpl;
import com.sparta.mvm.service.AuthService;
import com.sparta.mvm.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<SignupResponseDto>> signup(@Valid @RequestBody SignupRequestDto requestDto) {

        SignupResponseDto responseDto = userService.signup(requestDto);

        return ResponseEntity.ok().body(CommonResponse.<SignupResponseDto>builder()
                .msg("회원가입 성공")
                .statusCode(200)
                .data(responseDto)
                .build());
    }

    @GetMapping("/reissue")
    public ResponseEntity<CommonResponse<Void>> tokenReissuance() {
        return ResponseEntity.ok().body(CommonResponse.<Void>builder()
                .msg("토큰 재발급 성공")
                .statusCode(200)
                .build());
    }


    @GetMapping("/logout")
    public ResponseEntity<CommonResponse<Void>> logout(HttpServletResponse response, HttpServletRequest request) {
        authService.invalidateTokens(response, request);

        return ResponseEntity.ok().body(CommonResponse.<Void>builder()
                .msg("로그아웃 성공")
                .statusCode(200)
                .build());

    }


    @PutMapping("/resign")
    public ResponseEntity<String> resign(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ResignDto resignDto){

        userService.resign(userDetails.getUser(), resignDto);
        return ResponseEntity.ok("회원 탈퇴 완료");
    }
}
