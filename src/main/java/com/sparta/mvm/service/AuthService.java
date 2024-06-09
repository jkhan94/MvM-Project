package com.sparta.mvm.service;

/*

로그인 로직을 처리하는 서비스 클래스.

        - 사용자의 ID와 비밀번호를 검증하고, 검증이 성공하면 accessToken과 refreshToken을 생성하여 반환.

 */

import com.sparta.mvm.dto.LoginRequestDto;
import com.sparta.mvm.entity.User;
import com.sparta.mvm.jwt.JwtUtil;
import com.sparta.mvm.repository.UserRepository;
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
//    private final Map<String, RefreshTokenDto> refreshTokenDtoMap = new HashMap<>();

    //TokenResponseDto 는 로그인 성공 시 토큰 정보를 담기 위한 클래스
    @Transactional
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {

        User user = userRepository.findByUsername(loginRequestDto.getUsername())
                 .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        String refreshToken = jwtUtil.createRefreshToken(loginRequestDto.getUsername());
        jwtUtil.addRefreshJwtToCookie(refreshToken, response);

        String token = jwtUtil.createAccessToken(loginRequestDto.getUsername());
        jwtUtil.addAccessJwtToCookie(token, response);

        user.setRefreshToken(refreshToken);
    }

    // 액세스 토큰 재발급
    public String tokenReissuance(String refreshToken, HttpServletResponse res) throws IOException {
        String username = jwtUtil.getUserInfoFromToken(refreshToken).getSubject();
        // TODO : db에 존재하는지 체크
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException());

        // 클라이언트(현재로그인중, 리프레쉬토큰 만료x, 토큰만료 상태) 에서 보내온 refresh토큰과 db에 저장된,
        // 현재 로그인중인 username에 해당하는 refresh토큰 비교후 같으면 새로운 토큰발급
        String userTokenValue = jwtUtil.substringToken(user.getRefreshToken());
        // TODO : DB에 저장되어 있는 토큰값과 다를시 예외처리
        if (userTokenValue.equals(refreshToken)) {
            String newToken = jwtUtil.createAccessToken(username);
            jwtUtil.addAccessJwtToCookie(newToken, res);
            return newToken;
        }

        return "";
    }


//    // reFreshDto 값 저장하는 용도
//    public void setRefreshToken(String username, String refreshTokenValue) {
//        RefreshTokenDto tokenDto = new RefreshTokenDto(refreshTokenValue);
//        tokenDto.setTokenValid(true);
//        refreshTokenDtoMap.put(username, tokenDto);
//    }
//
//    // 요구사항중 reFresh 유효성 여부 검사하는 메서드
//    public boolean isRefreshTokenValid(String username) {
//        // TODO: 존재하는 유저명인지 체크
//        RefreshTokenDto tokenDto = refreshTokenDtoMap.get(username);
//        return tokenDto.isTokenValid();
//    }
//
//    public void setRefreshTokenValid(String username, boolean valid) {
//        // TODO: 존재하는 유저명인지 체크
//        refreshTokenDtoMap.get(username).setTokenValid(valid);
//    }


    // 데이터베이스에 초기 데이터를 넣는 로직
//    public void initTable() {
//        String password = "testPassword";
//        password = passwordEncoder.encode(password);
//        TestUser user = new TestUser("testUserId", password);
//        userRepository.save(user);
//    }
}