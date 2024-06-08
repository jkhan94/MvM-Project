package com.sparta.mvm.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.mvm.dto.LoginRequestDto;
import com.sparta.mvm.security.UserDetailsImpl;
import com.sparta.mvm.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthService authService;
    private LoginRequestDto requestDto;
    // 로그인 요청 url
    public JwtAuthenticationFilter(AuthService authService) {
        this.authService = authService;
        setFilterProcessesUrl("/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        authService.login(requestDto, response);
        successLogin(response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
    }

    private void successLogin(HttpServletResponse res) {
        try {
            res.setCharacterEncoding("UTF-8");
            res.getWriter().println("로그인이 성공하였습니다! (토큰/리프레시토큰 생성)");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}