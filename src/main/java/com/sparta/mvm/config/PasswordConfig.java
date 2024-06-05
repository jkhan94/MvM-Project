package com.sparta.mvm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        // interface PasswordEncoder 구현체
        // BCrypt는 해시함수. 강력해서 비밀번호 암호화(패스워드 인코딩)에 많이 쓰임.
        return new BCryptPasswordEncoder();
    }
}
