package com.sparta.mvm.controller;

import com.sparta.mvm.dto.SignupRequestDto;
import com.sparta.mvm.dto.SignupResponseDto;
import com.sparta.mvm.exception.CommonResponse;
import com.sparta.mvm.service.AuthService;
import com.sparta.mvm.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CommonResponse<SignupResponseDto>> signup(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
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
                .build()); // dto 리턴 가입할 떄 어떤걸 넣을지
    }

    @GetMapping("/reissue")
    public ResponseEntity<CommonResponse<Void>> tokenReissuance() {
        return ResponseEntity.ok().body(CommonResponse.<Void>builder()
                .statusCode(200)
                .msg("토큰 재발급 성공")
                .build());
    }


    @GetMapping("/logout")
    public ResponseEntity<CommonResponse<Void>> logout(HttpServletResponse response, HttpServletRequest request) {
        authService.invalidateTokens(response, request);

        return ResponseEntity.ok().body(CommonResponse.<Void>builder()
                .statusCode(200)
                .msg("로그아웃 성공")
                .build());

    }


//    @PutMapping("/resign/{username}")
//    //jwt 관련해서 refresh토큰 jwt 회원탈퇴
//    public
//    }
}
