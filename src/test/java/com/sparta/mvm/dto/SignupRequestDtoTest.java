package com.sparta.mvm.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SignupRequestDtoTest {
    SignupRequestDto requestDto;

    @Test
    @DisplayName("Setter 동작 확인")
    void testSetter(){
        // givne
        String username = "HanId";
        String password = "Han#666666";
        String name="Han";
        String lineIntro="I am Han";
        String email="han@example.com";
        requestDto = new SignupRequestDto();

        // when
        requestDto.setUsername(username);
        requestDto.setPassword(password);
        requestDto.setName(name);
        requestDto.setLineIntro(lineIntro);
        requestDto.setEmail(email);

        // then
        assertNotNull(requestDto);
        assertEquals(username,requestDto.getUsername());
        assertEquals(password,requestDto.getPassword());
        assertEquals(name,requestDto.getName());
        assertEquals(lineIntro,requestDto.getLineIntro());
        assertEquals(email,requestDto.getEmail());
    }

}