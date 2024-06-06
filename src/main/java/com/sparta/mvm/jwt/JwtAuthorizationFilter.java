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

        if(!req.getRequestURI().equals("/user/login")) {
            CheckValidToken isCheckToken = new CheckValidToken();
            String tokenValue = jwtUtil.getAccessTokenFromRequest(req);
            String refreshTokenValue = jwtUtil.getRefreshTokenFromRequest(req);

            if (StringUtils.hasText(tokenValue) && StringUtils.hasText(refreshTokenValue)) {
                // JWT 토큰 substring
                tokenValue = jwtUtil.substringToken(tokenValue);
                refreshTokenValue = jwtUtil.substringToken(refreshTokenValue);

                jwtUtil.setIsCheckToken(tokenValue, refreshTokenValue, isCheckToken);
                // jwtAccess토큰 오류검증
                if (!isCheckToken.isValidToken()) {
                    return;
                }
                // jwt 토큰들 둘다 만료되면 상태코드 반환및 재로그인 요청
                if (isCheckToken.isExpiredToken() && isCheckToken.isExpiredRefreshToken()) {
                    return;
                }

                try {
                    // 재발급 api가 아닐경우만 인증객체 생성시도
                    if (!req.getRequestURI().equals("/user/reissue")) {
                        // 현재 만료토큰에 대한 예외처리 메시지가 없어서 만료된 상태로 일반 API 요청하면 아무반응없음
                        Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
                        setAuthentication(info.getSubject());
                    } else {
                        authService.tokenReissuance(refreshTokenValue, res);
                    }
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