package com.sparta.mvm.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileRequestDtoTest {
    ProfileRequestDto requestDto;

    @Test
    @DisplayName("생성자로 dto 생성: 비밀번호 변경 안 함")
    void createDtoWithoutPassword(){
        // given
        String name="이름";
        String lineIntro="한 줄 소개";
        String currentPassword = "";
        String changedPassword = "";

        // when
        requestDto=new ProfileRequestDto(name,lineIntro,currentPassword,changedPassword);

        // then
        assertNotNull(requestDto);
        assertEquals(name,requestDto.getName());
        assertEquals(lineIntro,requestDto.getLineIntro());
        assertEquals(currentPassword,requestDto.getCurrentPassword());
        assertEquals(changedPassword,requestDto.getChangedPassword());
    }

    @Test
    @DisplayName("생성자로 dto 생성: 비밀번호 변경함")
    void createDtoWithPassword(){
        // given
        String name="이름";
        String lineIntro="한 줄 소개";
        String currentPassword = "#han567899";
        String changedPassword = "#han56789";

        // when
        requestDto=new ProfileRequestDto(name,lineIntro,currentPassword,changedPassword);

        // then
        assertNotNull(requestDto);
        assertEquals(name,requestDto.getName());
        assertEquals(lineIntro,requestDto.getLineIntro());
        assertEquals(currentPassword,requestDto.getCurrentPassword());
        assertEquals(changedPassword,requestDto.getChangedPassword());
    }

    @Test
    @DisplayName("setter 확인")
    void testSetter(){
        // given
        String changedPassword = "#han56789";
        requestDto = new ProfileRequestDto();

        // when
        requestDto.setChangedPassword(changedPassword);

        // then
        assertEquals(changedPassword,requestDto.getChangedPassword());

    }

}