package com.sparta.mvm.dto;

import com.sparta.mvm.entity.User;
import com.sparta.mvm.entity.UserStatusEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SignupResponseDtoTest {
    SignupResponseDto requestDto;

    @Test
    @DisplayName("생성자로 DTO 생성 확인")
    void createDtoWithArguments(){
        // given
        String username = "HanId";
        String name="Han";
        String lineIntro="I am Han";
        LocalDateTime createdAt = LocalDateTime.of(2024, 6, 14, 15, 47, 50);
        LocalDateTime modifiedAt = LocalDateTime.now();
        String userStatusEnum="정상";

        // when
        requestDto = new SignupResponseDto(username,name,lineIntro,createdAt,modifiedAt,userStatusEnum);

        // then
        assertNotNull(requestDto);
        assertEquals(username,requestDto.getUsername());
        assertEquals(name,requestDto.getName());
        assertEquals(lineIntro,requestDto.getLineIntro());
        assertEquals(createdAt,requestDto.getCreatedAt());
        assertEquals(modifiedAt,requestDto.getModifiedAt());
        assertEquals(userStatusEnum,requestDto.getUserStatusEnum());
    }

    @Test
    @DisplayName("User로 DTO 생성 확인")
    void createDtoWithUser(){
        // given
        String username = "hanid";
        String password = "#han56789";
        String name = "Han";
        String email = "han@example.com";
        String lineIntro = "It's Han";
        UserStatusEnum userStatus = UserStatusEnum.USER_NORMAL;
        User user = new User(username, password, name, email, lineIntro, userStatus);

        // when
        requestDto = new SignupResponseDto(user);

        // then
        assertNotNull(requestDto);
        assertEquals(user.getUsername(),requestDto.getUsername());
        assertEquals(user.getName(),requestDto.getName());
        assertEquals(user.getLineIntro(),requestDto.getLineIntro());
        assertEquals(user.getCreatedAt(),requestDto.getCreatedAt());
        assertEquals(user.getModifiedAt(),requestDto.getModifiedAt());
        assertEquals(user.getUserStatus().getStatus(),requestDto.getUserStatusEnum());
    }

}