package com.sparta.mvm.dto;

import com.sparta.mvm.entity.User;
import com.sparta.mvm.entity.UserStatusEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProfileResponseDtoTest {
    ProfileResponseDto responseDto;

    @Test
    @DisplayName("User로 DTO 생성")
    void createDtoWithUser() {
        // given
        String username = "hanid";
        String password = "#han56789";
        String name = "Han";
        String email = "han@example.com";
        String lineIntro = "It's Han";
        UserStatusEnum userStatus = UserStatusEnum.USER_NORMAL;
        User user = new User(username, password, name, email, lineIntro, userStatus);

        // when
        responseDto = new ProfileResponseDto(user);

        // then
        assertNotNull(responseDto);
        assertEquals(user.getUsername(), responseDto.getUsername());
        assertEquals(user.getName(), responseDto.getName());
        assertEquals(user.getLineIntro(), responseDto.getLineIntro());
        assertEquals(user.getEmail(), responseDto.getEmail());
    }

}