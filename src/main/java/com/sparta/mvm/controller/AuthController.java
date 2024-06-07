//package com.sparta.mvm.controller;
//
//
//
//// 로그인 요청을 처리하는 컨트롤러 클래스
//
//
//import com.sparta.mvm.dto.LoginRequestDto;
//import com.sparta.mvm.service.AuthService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("api/auth")
//public class AuthController {
//    private final AuthService authService;
//
//    @PostMapping("/login")
//    public ResposeEntity<TokenResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
//        TokenResponseDto tokenResponseDto = authService.login(loginRequestDto);
//        return ResponseEntity.ok(tokenRequestDto);
//    }
//}
