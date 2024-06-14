package com.sparta.mvm.dto;

import com.sparta.mvm.entity.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PostRequestDtoTest {
    PostRequestDto requestDto;

    @Test
    @DisplayName("Post 요청 DTO 생성")
    void createPostRequestDto() {
        // given
        String contents = "I am content";

        // when
        requestDto = new PostRequestDto(contents);

        // then
        assertNotNull(requestDto);
        assertEquals(contents, requestDto.getContents());
    }

    @Test
    @DisplayName("Post 요청 DTO의 toEntity 메소드")
    void toEntity() {
        // given
        String contents = "I am content";
        requestDto = new PostRequestDto(contents);

        // when
        Post post = requestDto.toEntity();

        // then
        assertNotNull(post);
        assertEquals(contents, post.getContents());
    }
}