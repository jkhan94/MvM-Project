package com.sparta.mvm.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.mvm.dto.LoginRequestDto;
import com.sparta.mvm.exception.ErrorEnum;
import com.sparta.mvm.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthService authService;
    private LoginRequestDto loginRequestDto;

    // 로그인 요청 url
    public JwtAuthenticationFilter(AuthService authService) {
        this.authService = authService;
        setFilterProcessesUrl("/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            loginRequestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getUsername(),
                            loginRequestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        authService.login(loginRequestDto, response);
        successLogin(response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        if(failed instanceof InternalAuthenticationServiceException) {
            request.setAttribute("USER_NOT_FOUND", ErrorEnum.USER_NOT_FOUND);
            throw new IllegalArgumentException();
        }
        else if(failed instanceof BadCredentialsException) {
            request.setAttribute("BAD_PASSWORD", ErrorEnum.BAD_PASSWORD);
            throw new IllegalArgumentException();
        }

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