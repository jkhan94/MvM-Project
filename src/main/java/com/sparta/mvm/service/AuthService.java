package com.sparta.mvm.service;

/*

로그인 로직을 처리하는 서비스 클래스.

        - 사용자의 ID와 비밀번호를 검증하고, 검증이 성공하면 accessToken과 refreshToken을 생성하여 반환.

 */


import com.sparta.mvm.AuthTest.RefreshTokenDto;
import com.sparta.mvm.AuthTest.TestUser;
import com.sparta.mvm.AuthTest.TestUserRepository;
import com.sparta.mvm.config.PasswordConfig;
import com.sparta.mvm.dto.LoginRequestDto;
import com.sparta.mvm.dto.TokenResponseDto;
import com.sparta.mvm.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TestUserRepository userRepository;
    private final PasswordConfig passwordConfig;
    private final Map<String, RefreshTokenDto> refreshTokenDtoMap = new HashMap<>();


    public TokenResponseDto login(LoginRequestDto loginRequestDto) { //TokenResponseDto 는 로그인 성공 시 토큰 정보를 담기 위한 클래스
        TestUser user = userRepository.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
        // orElseThrow 메서드 오류 =>  TestUserRepository에서 findByUsername 메서드가 Optional<TestUser>을 반환하도록 수정

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        String accessToken = jwtUtil.createAccessToken(user.getUsername());
        String refreshToken = jwtUtil.createRefreshToken(user.getUsername());

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new TokenResponseDto("Login successful", 200, accessToken, refreshToken);
    }


//    @Transactional
//    public void saveRefreshToken(String username, String refreshToken) {
//        Optional<TestUser> user = userRepository.findByUsername(username);
//        user.setRefreshToken(refreshToken);
//    }

    // 수정 된 saveRefreshToken 메서드
    @Transactional
    public void saveRefreshToken(String username, String refreshToken) {
        TestUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setRefreshToken(refreshToken);
    }


    // 액세스 토큰 재발급
    public void tokenReissuance(String refreshToken, HttpServletResponse res) throws IOException {
        String username = jwtUtil.getUserInfoFromToken(refreshToken).getSubject();
        // TODO : db에 존재하는지 체크
        Optional<TestUser> user = userRepository.findByUsername(username);

        // 클라이언트(현재로그인중, 리프레쉬토큰 만료x, 토큰만료 상태) 에서 보내온 refresh토큰과 db에 저장된,
        // 현재 로그인중인 username에 해당하는 refresh토큰 비교후 같으면 새로운 토큰발급
        String userTokenValue = jwtUtil.substringToken(user.get().getRefreshToken());
        if (userTokenValue.equals(refreshToken)) {
            String newToken = jwtUtil.createAccessToken(username);
            jwtUtil.addAccessJwtToCookie(newToken, res);
        }
        res.setStatus(200);
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write("토큰 재발급 성공");
    }


    // reFreshDto 값 저장하는 용도
    public void setRefreshToken(String username, String refreshTokenValue) {
        RefreshTokenDto tokenDto = new RefreshTokenDto(refreshTokenValue);
        tokenDto.setTokenValid(true);
        refreshTokenDtoMap.put(username, tokenDto);
    }

    // 요구사항중 reFresh 유효성 여부 검사하는 메서드
    public boolean isRefreshTokenValid(String username) {
        // TODO: 존재하는 유저명인지 체크
        RefreshTokenDto tokenDto = refreshTokenDtoMap.get(username);
        return tokenDto.isTokenValid();
    }

    public void setRefreshTokenValid(String username, boolean valid) {
        // TODO: 존재하는 유저명인지 체크
        refreshTokenDtoMap.get(username).setTokenValid(valid);
    }
}