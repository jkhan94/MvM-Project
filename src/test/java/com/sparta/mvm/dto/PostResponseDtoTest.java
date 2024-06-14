package com.sparta.mvm.dto;

import com.sparta.mvm.entity.Post;
import com.sparta.mvm.entity.User;
import com.sparta.mvm.entity.UserStatusEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PostResponseDtoTest {
    PostResponseDto responseDto;

    @Test
    @DisplayName("PostResponseDto 생성")
    void createDto() {
        // given
        String msg = "반환 메시지";
        int statusCode = 200;
        Long id = 1L;
        String username = "Han";
        String contents = "I am Han";
        LocalDateTime createdAt = LocalDateTime.of(2024, 6, 14, 15, 47, 50);
        LocalDateTime modifiedAt = LocalDateTime.now();

        // when
        responseDto = new PostResponseDto(msg,statusCode,id,username,contents,createdAt,modifiedAt);

        // then
        assertNotNull(responseDto);
        assertEquals(msg, responseDto.getMsg());
        assertEquals(statusCode, responseDto.getStatusCode());
        assertEquals(id, responseDto.getId());
        assertEquals(username, responseDto.getUsername());
        assertEquals(contents, responseDto.getContents());
        assertEquals(createdAt, responseDto.getCreatedAt());
        assertEquals(modifiedAt, responseDto.getModifiedAt());
    }

    @Test
    @DisplayName("PostResponseDto의 toDto 메소드")
    void toDto() {
        // given
        String msg = "반환 메시지";
        int statusCode = 200;
        String contents = "I am Han";
        String username = "hanid";
        String password = "#han56789";
        String name = "Han";
        String email = "han@example.com";
        String lineIntro = "It's Han";
        UserStatusEnum userStatus = UserStatusEnum.USER_NORMAL;
        User user = new User(username, password, name, email, lineIntro, userStatus);
        Post post =  new Post(contents);
        post.setUser(user);

        // when
        responseDto = PostResponseDto.toDto(msg,statusCode,post);

        // then
        assertNotNull(responseDto);
        assertEquals(msg, responseDto.getMsg());
        assertEquals(statusCode, responseDto.getStatusCode());
        assertEquals(post.getId(), responseDto.getId());
        assertEquals(username, responseDto.getUsername());
        assertEquals(contents, responseDto.getContents());
        assertEquals(post.getCreatedAt(), responseDto.getCreatedAt());
        assertEquals(post.getModifiedAt(), responseDto.getModifiedAt());
    }

    @Test
    @DisplayName("PostResponseDto의 toDeleteResponse 메소드")
    void toDeleteResponse() {
        // given
        String msg = "반환 메시지";
        int statusCode = 200;

        // when
        responseDto = PostResponseDto.toDeleteResponse(msg,statusCode);

        // then
        assertNotNull(responseDto);
        assertEquals(msg, responseDto.getMsg());
        assertEquals(statusCode, responseDto.getStatusCode());
    }
}