package com.sparta.mvm.AuthTest;

import com.sparta.mvm.config.PasswordConfig;
import com.sparta.mvm.jwt.JwtUtil;
import com.sparta.mvm.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final TestUserRepository userRepository;
    private final PasswordConfig passwordConfig;

    @Transactional
    public void login(String username, String refreshToken) {
        TestUser user = userRepository.findByUsername(username);
        user.setRefreshToken(refreshToken);
    }

    public void tokenReissuance(String refreshToken, HttpServletResponse res) {
        String username = jwtUtil.getUserInfoFromToken(refreshToken).getSubject();
        TestUser user = userRepository.findByUsername(username);

        // 클라이언트(현재로그인중, 리프레쉬토큰 만료x, 토큰만료 상태) 에서 보내온 refresh토큰과 db에 저장된
        // 현재 로그인중인 username에 해당하는 refresh토큰 비교후 같으면 새로운 토큰발급
        String userTokenValue = user.getRefreshToken().substring(7);
        if(userTokenValue.equals(refreshToken)) {
            String newToken = jwtUtil.createAccessToken(username);
            jwtUtil.addAccessJwtToCookie(newToken, res);
        }
    }

    public void initTable() {
        String password = "testPassword";
        password = passwordConfig.passwordEncoder().encode(password);
        TestUser user = new TestUser("testUserId", password);
        userRepository.save(user);
    }
}
