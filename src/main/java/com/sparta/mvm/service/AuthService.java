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


    // 로그인을 시도할 때 호출.
    // 사용자의 아이디와 비밀번호를 검증하고, 성공적으로 인증 시 JWT 토큰을 생성하여 사용자에게 반환.
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {

        User user = userRepository.findByUsername(loginRequestDto.getUsername())
                 .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
        // 사용자 검색
        User user = userRepository.findByUsername(loginRequestDto.getUsername()) // 사용자가 입력한 아이디(username)로 데이터베이스에서 해당 사용자를 찾음.
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password")); // 만약 사용자가 DB에 없다면, 예외를 던져 로그인 시도가 실패했음을 알림.

        // 비밀번호 검증
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) { // 사용자가 입력한 비밀번호와 DB에 저장된 비밀번호를 비교.
            // => 비밀번호 일치하지 않을 시 예외를 던져 로그인 시도가 실패함을 문장을 통해서 알림.
            throw new IllegalArgumentException("Invalid username or password");
        }


        // Refresh Token 생성 및 쿠키 저장
        String refreshToken = jwtUtil.createRefreshToken(loginRequestDto.getUsername()); // 사용자의 ID를 기반으로 새로운 리프레시 토큰을 생성.
        jwtUtil.addRefreshJwtToCookie(refreshToken, response); // 생성된 리프레시 토큰을 HTTP 응답의 쿠키에 추가하여 클라이언트에게 반환.


        // Access Token 생성 및 쿠키 저장
        String token = jwtUtil.createAccessToken(loginRequestDto.getUsername()); // 사용자의 ID를 기반으로 새로운 Access 토큰을 생성.
        jwtUtil.addAccessJwtToCookie(token, response); // 생성된 액세스 토큰을 HTTP 응답의 쿠키에 추가하여 클라이언트에게 반환.

        user.setRefreshToken(refreshToken); // 생성된 리프레시 토큰을 사용자의 DB 레코드에 저장. => 나중에 사용자가 리프레시 토큰을 사용할 때 검증하기 위해 필요.
    }



    // 액세스 토큰 재발급
    public String tokenReissuance(String refreshToken, HttpServletResponse res) throws IOException {
        String username = jwtUtil.getUserInfoFromToken(refreshToken).getSubject(); // 리프레스 토큰에서 사용자 ID를 추출.
        // TODO : db에 존재하는지 체크
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException());
        // 추출한 사용자 ID를 기반으로 DB에서 해당 사용자 검색. / 사용자가 DB에 없다면, 예외를 던져 처리.

        // 클라이언트(현재로그인중, 리프레쉬토큰 만료x, 토큰만료 상태) 에서 보내온 refresh토큰과 db에 저장된,
        // 현재 로그인중인 username에 해당하는 refresh토큰 비교후 같으면 새로운 토큰발급
        String userTokenValue = jwtUtil.substringToken(user.getRefreshToken()); // 사용자 ID 기반으로 새로운 Access 토큰 생성.
        // TODO : DB에 저장되어 있는 토큰값과 다를시 예외처리
        if (userTokenValue.equals(refreshToken)) {
            String newToken = jwtUtil.createAccessToken(username);
            jwtUtil.addAccessJwtToCookie(newToken, res); // 생성된 엑세스 토큰을 HTTP 응답의 쿠키에 추가하여 클라이언트에게 반환.
            return newToken; // 생성된 엑세스 토큰 반환.
        }

        return "";
    }

    // 사용자의 토큰을 삭제하여 로그아웃 처리
    public void invalidateTokens(String username, HttpServletResponse res) {
        // 사용자 이름으로 사용자를 데이터베이스에서 찾습니다.
        User user = userRepository.findByUsername(username)
                // 만약 사용자를 찾지 못하면 예외를 던집니다.
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 사용자의 리프레시 토큰을 삭제하여 토큰을 무효화합니다.

        jwtUtil.initJwtCookie(res);
        user.setRefreshToken(null); // 리프레시 토큰을 null로 설정하여 초기화합니다.
        userRepository.save(user); // 변경된 사용자 정보를 데이터베이스에 저장합니다.
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