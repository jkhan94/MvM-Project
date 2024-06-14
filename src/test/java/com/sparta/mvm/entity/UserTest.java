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

    /*
    User 엔티티의 update 함수
     public void update(ProfileRequestDto requestDto) {
        this.name = requestDto.getName();
        this.lineIntro = requestDto.getLineIntro();
        this.password = requestDto.getChangedPassword();
    }
    requestDto를 인자로 수정하는데 엔티티와 서비스 중 어디서 테스트해야 하는지 궁금합니다.
     */
    @Test
    @DisplayName("회원정보 비밀번호 업데이트 확인")
    void updateUser(){
        // given
        String changedPassword = "#han56789";

        ProfileRequestDto requestDto =  new ProfileRequestDto();
        requestDto.setChangedPassword(changedPassword);

        // when
        user.update(requestDto);

        // then
        assertEquals(changedPassword, user.getPassword());

    }
}