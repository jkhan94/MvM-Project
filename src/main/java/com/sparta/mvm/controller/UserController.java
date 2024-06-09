package com.sparta.mvm.controller;

import com.sparta.mvm.dto.LoginRequestDto;
import com.sparta.mvm.dto.ResignDto;
import com.sparta.mvm.dto.SignupRequestDto;
import com.sparta.mvm.dto.SignupResponseDto;
import com.sparta.mvm.exception.CommonResponse;
import com.sparta.mvm.security.UserDetailsImpl;
import com.sparta.mvm.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<SignupResponseDto>> signup(@Valid@RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            throw new IllegalArgumentException("회원 가입 실패");
        }

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
                .statusCode(200)
                .msg("토큰 재발급 성공")
                .build());
    }

    @PutMapping("/resign")
    public ResponseEntity<String> resign(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ResignDto resignDto){

        userService.resign(userDetails.getUser(), resignDto);
        return ResponseEntity.ok("회원 탈퇴 완료");
    }

}
