package com.sparta.mvm.service;

import com.sparta.mvm.dto.LoginRequestDto;
import com.sparta.mvm.entity.User;
import com.sparta.mvm.entity.UserStatusEnum;
import com.sparta.mvm.exception.ErrorEnum;
import com.sparta.mvm.jwt.JwtUtil;
import com.sparta.mvm.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    // 사용자의 아이디와 비밀번호를 검증하고, 성공적으로 인증 시 JWT 토큰을 생성하여 사용자에게 반환.
    @Transactional
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response, HttpServletRequest request) {

        User user = userRepository.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        // 비밀번호 검증
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) { // 사용자가 입력한 비밀번호와 DB에 저장된 비밀번호를 비교.
            // => 비밀번호 일치하지 않을 시 예외를 던져 로그인 시도가 실패함을 문장을 통해서 알림.
            throw new IllegalArgumentException("Invalid username or password");
        }
        // 사용자 상태 검증
        if (user.getUserStatus().equals(UserStatusEnum.USER_RESIGN)) {
            request.setAttribute("test", ErrorEnum.BAD_RESIGN);
            throw new IllegalArgumentException("Invalid username or password");
        }

        // Refresh Token 생성 및 쿠키 저장
        String refreshToken = jwtUtil.createRefreshToken(loginRequestDto.getUsername());
        jwtUtil.addRefreshJwtToCookie(refreshToken, response);

        // Access Token 생성 및 쿠키 저장
        String token = jwtUtil.createAccessToken(loginRequestDto.getUsername());
        jwtUtil.addAccessJwtToCookie(token, response);

        user.setRefreshToken(refreshToken); // 생성된 리프레시 토큰을 사용자의 DB 레코드에 저장. => 나중에 사용자가 리프레시 토큰을 사용할 때 검증하기 위해 필요.
    }

    // 액세스 토큰 재발급
    public String tokenReissuance(String refreshToken, HttpServletResponse res) throws IOException {
        String username = jwtUtil.getUserInfoFromToken(refreshToken).getSubject(); // 리프레스 토큰에서 사용자 ID를 추출.

        // 추출한 사용자 ID를 기반으로 DB에서 해당 사용자 검색. / 사용자가 DB에 없다면, 예외를 던져 처리.
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException());

        String userTokenValue = jwtUtil.substringToken(user.getRefreshToken());
        if (userTokenValue.equals(refreshToken)) {
            String newToken = jwtUtil.createAccessToken(username);
            jwtUtil.addAccessJwtToCookie(newToken, res); // 생성된 엑세스 토큰을 HTTP 응답의 쿠키에 추가하여 클라이언트에게 반환.
            return newToken; // 생성된 엑세스 토큰 반환.
        }
        return "";
    }

    // 사용자의 토큰을 삭제하여 로그아웃 처리
    public void invalidateTokens(HttpServletResponse response, HttpServletRequest request) {
        // 사용자 이름으로 사용자를 데이터베이스에서 찾습니다.

        String value = jwtUtil.getAccessTokenFromRequest(request);
        value = jwtUtil.substringToken(value);
        String username = jwtUtil.getUserInfoFromToken(value).getSubject();


        User user = userRepository.findByUsername(username)
                // 만약 사용자를 찾지 못하면 예외를 던집니다.
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 사용자의 리프레시 토큰을 삭제하여 토큰을 무효화합니다.
        jwtUtil.initJwtCookie(response);
        user.setRefreshToken(null);
        userRepository.save(user);
    }
}