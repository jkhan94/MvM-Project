package com.sparta.mvm.jwt;

import com.sparta.mvm.exception.ErrorEnum;
import com.sparta.mvm.security.UserDetailsServiceImpl;
import com.sparta.mvm.service.AuthService;
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


        if(!(req.getRequestURI().equals("/users/login") || req.getRequestURI().equals("/users/signup"))) {
            if (!(StringUtils.hasText(tokenValue) && StringUtils.hasText(refreshTokenValue))) {
                req.setAttribute("NOT_VALID_TOKEN", ErrorEnum.NOT_VALID_TOKEN);
                throw new IllegalArgumentException();
            }
            tokenValue = jwtUtil.substringToken(tokenValue);
            refreshTokenValue = jwtUtil.substringToken(refreshTokenValue);
            if (req.getRequestURI().equals("/users/reissue")) {
                jwtUtil.validToken(refreshTokenValue, JwtTokenType.REFRESH_TOKEN, req);
                tokenValue = authService.tokenReissuance(refreshTokenValue, res);
                tokenValue = jwtUtil.substringToken(tokenValue);
            } else {
                jwtUtil.validToken(refreshTokenValue, JwtTokenType.REFRESH_TOKEN, req);
                jwtUtil.validToken(tokenValue, JwtTokenType.ACCESS_TOKEN, req);
            }
            Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
            setAuthentication(info.getSubject());
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