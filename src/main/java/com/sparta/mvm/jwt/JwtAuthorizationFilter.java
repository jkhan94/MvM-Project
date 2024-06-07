package com.sparta.mvm.jwt;

import com.sparta.mvm.AuthTest.AuthService;
import com.sparta.mvm.AuthTest.CheckValidToken;
import com.sparta.mvm.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthService authService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService, AuthService authService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.authService = authService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = jwtUtil.getAccessTokenFromRequest(req);
        String refreshTokenValue = jwtUtil.getRefreshTokenFromRequest(req);
        // JWT 토큰 substring
        tokenValue = jwtUtil.substringToken(tokenValue);
        refreshTokenValue = jwtUtil.substringToken(refreshTokenValue);
        // 재발급 요청, 로그인 요청시 검증 X  +재발급 요청의 경우 토큰 재발급 메서드 실행
        if (req.getRequestURI().equals("/user/reissue")) {
            authService.tokenReissuance(refreshTokenValue, res);
        }
        else if (!req.getRequestURI().equals("/user/login")) {
            CheckValidToken isCheckToken = new CheckValidToken();

            if (StringUtils.hasText(tokenValue) && StringUtils.hasText(refreshTokenValue)) {

                jwtUtil.setIsCheckToken(tokenValue, refreshTokenValue, isCheckToken);
                // jwtAccess토큰 오류검증
                if (!jwtUtil.validateToken(tokenValue)) {
                    return;
                }
                // jwtRefresh토큰 오류검증
                if (!jwtUtil.validateToken(refreshTokenValue)) {
                    return;
                }
                // TODO: 리프레쉬 토큰 만료시 재로그인 필요 메시지, 상태코드 클라에 반환하기
                if (isCheckToken.isExpiredRefreshToken()) {

                }
                // TODO: 액세스 토큰 만료시 재발급 필요 메시지, 상태코드 클라에 반환하기
                if (isCheckToken.isExpiredToken()) {

                }
                // 인증객체 생성
                try {
                    Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
                    setAuthentication(info.getSubject());

                } catch (Exception e) {
                    return;
                }
            }
        }

        filterChain.doFilter(req, res);
    }

    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, null, null);
    }
}