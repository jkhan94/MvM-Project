package com.sparta.mvm.dto;

import com.sparta.mvm.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LikeResponseDtoTest {
    LikeResponseDto responseDto;

    @Test
    @DisplayName("dto 생성")
    void createDto() {
        // given
        String msg = "반환 메시지";
        int statusCode = 200;
        Long id = 1L;
        LocalDateTime createdAt = LocalDateTime.of(2024, 6, 14, 15, 47, 50);
        LocalDateTime modifiedAt = LocalDateTime.now();

        // when
        responseDto = new LikeResponseDto(msg, statusCode, id, createdAt, modifiedAt);

        // then
        assertNotNull(responseDto);
        assertEquals(msg, responseDto.getMsg());
        assertEquals(statusCode, responseDto.getStatusCode());
        assertEquals(id, responseDto.getId());
        assertEquals(createdAt, responseDto.getCreatedAt());
        assertEquals(modifiedAt, responseDto.getModifiedAt());
    }

    @Test
    @DisplayName("toDto 메소드: Like(user, post)")
    void toDtoWithPost() {
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
        Post post = new Post(contents);
        Like like = new Like(user, post);

        // when
        responseDto = LikeResponseDto.toDto(msg, statusCode, like);

        // then
        assertNotNull(responseDto);
        assertEquals(msg, responseDto.getMsg());
        assertEquals(statusCode, responseDto.getStatusCode());
        assertEquals(like.getId(), responseDto.getId());
        assertEquals(like.getCreatedAt(), responseDto.getCreatedAt());
        assertEquals(like.getModifiedAt(), responseDto.getModifiedAt());
    }

    @Test
    @DisplayName("toDto 메소드: Like(user, comment)")
    void toDtoWithComment() {
        // given
        String msg = "반환 메시지";
        int statusCode = 200;
        String comments = "댓글입니다";
        String contents = "I am Han";
        String username = "hanid";
        String password = "#han56789";
        String name = "Han";
        String email = "han@example.com";
        String lineIntro = "It's Han";
        UserStatusEnum userStatus = UserStatusEnum.USER_NORMAL;
        User user = new User(username, password, name, email, lineIntro, userStatus);
        Post post =  new Post(contents);
        Comment comment = new Comment(comments,post);
        Like like = new Like(user, comment);

        // when
        responseDto = LikeResponseDto.toDto(msg, statusCode, like);

        // then
        assertNotNull(responseDto);
        assertEquals(msg, responseDto.getMsg());
        assertEquals(statusCode, responseDto.getStatusCode());
        assertEquals(like.getId(), responseDto.getId());
        assertEquals(like.getCreatedAt(), responseDto.getCreatedAt());
        assertEquals(like.getModifiedAt(), responseDto.getModifiedAt());
    }

    @Test
    @DisplayName("toDeleteResponse 메소드: Like(user, post)")
    void toDeleteResponseWithPost() {
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
        Post post = new Post(contents);
        Like like = new Like(user, post);

        // when
        responseDto = LikeResponseDto.toDeleteResponse(msg,statusCode,like);

        // then
        assertNotNull(responseDto);
        assertEquals(msg, responseDto.getMsg());
        assertEquals(statusCode, responseDto.getStatusCode());
        assertEquals(like.getId(), responseDto.getId());
        assertEquals(like.getCreatedAt(),responseDto.getCreatedAt());
        assertEquals(like.getModifiedAt(),responseDto.getModifiedAt());
    }

    @Test
    @DisplayName("toDeleteResponse 메소드: Like(user, comment)")
    void toDeleteResponseWithCommentt() {
        // given
        String msg = "반환 메시지";
        int statusCode = 200;
        String comments = "댓글입니다";
        String contents = "I am Han";
        String username = "hanid";
        String password = "#han56789";
        String name = "Han";
        String email = "han@example.com";
        String lineIntro = "It's Han";
        UserStatusEnum userStatus = UserStatusEnum.USER_NORMAL;
        User user = new User(username, password, name, email, lineIntro, userStatus);
        Post post =  new Post(contents);
        Comment comment = new Comment(comments,post);
        Like like = new Like(user, comment);

        // when
        responseDto = LikeResponseDto.toDeleteResponse(msg,statusCode,like);

        // then
        assertNotNull(responseDto);
        assertEquals(msg, responseDto.getMsg());
        assertEquals(statusCode, responseDto.getStatusCode());
        assertEquals(like.getId(), responseDto.getId());
        assertEquals(like.getCreatedAt(),responseDto.getCreatedAt());
        assertEquals(like.getModifiedAt(),responseDto.getModifiedAt());
    }
}