package com.sparta.mvm.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoginRequestDtoTest {
    LoginRequestDto requestDto;

    @Test
    @DisplayName("Setter 동작 확인")
    void testSetter() {
        // given
        String username = "Hello";
        String password = "World#11111";

        // when
        requestDto = new LoginRequestDto();
        requestDto.setUsername(username);
        requestDto.setPassword(password);

        // then
        assertNotNull(requestDto);
        assertEquals(username, requestDto.getUsername());
        assertEquals(password, requestDto.getPassword());
    }


}