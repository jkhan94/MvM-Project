package com.sparta.mvm.entity;

import com.sparta.mvm.dto.ProfileRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {
    User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    @DisplayName("User 생성 확인")
    void createUser() {
        // given
        String username = "hanid";
        String password = "#han56789";
        String name = "Han";
        String email = "han@example.com";
        String lineIntro = "It's Han";
        UserStatusEnum userStatus = UserStatusEnum.USER_NORMAL;

        // when
        user = new User(username, password, name, email, lineIntro, userStatus);

        // then
        assertNotNull(user);
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(lineIntro, user.getLineIntro());
        assertEquals(userStatus, user.getUserStatus());
    }

    @Test
    @DisplayName("회원정보 비밀번호 제외 업데이트 확인")
    void updateUser() {
        // given
        String name = "이름";
        String lineIntro = "한 줄 소개";
        String currentPassword = "";
        String changedPassword = "";

        ProfileRequestDto requestDto = new ProfileRequestDto(name, lineIntro, currentPassword, changedPassword);

        // when
        user.update(requestDto);

        // then
        assertEquals(changedPassword, user.getPassword());
    }

    @Test
    @DisplayName("회원정보 비밀번호 포함, 업데이트 확인")
    void updateUseWithPassword() {
        // given
        String name = "이름";
        String lineIntro = "한 줄 소개";
        String currentPassword = "#han567899";
        String changedPassword = "#han56789";

        ProfileRequestDto requestDto = new ProfileRequestDto(name, lineIntro, currentPassword, changedPassword);
        requestDto.setChangedPassword(changedPassword);

        // when
        user.update(requestDto);

        // then
        assertEquals(changedPassword, user.getPassword());
    }

    @Test
    @DisplayName("User 엔티티 resignStatus")
    void resignStatus() {
        UserStatusEnum userStatus = UserStatusEnum.USER_RESIGN;

        // when
        user.resignStatus();

        // then
        assertEquals(userStatus, user.getUserStatus());
    }
}