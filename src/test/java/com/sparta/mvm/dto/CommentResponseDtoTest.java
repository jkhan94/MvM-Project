package com.sparta.mvm.dto;

import com.sparta.mvm.entity.Comment;
import com.sparta.mvm.entity.Post;
import com.sparta.mvm.entity.User;
import com.sparta.mvm.entity.UserStatusEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CommentResponseDtoTest {
    CommentResponseDto responseDto;

    @Test
    @DisplayName("Dto 생성")
    void createDto() {
        // given
        String msg = "반환 메시지";
        int statusCode = 200;
        Long id = 1L;
        String username = "Han";
        String comments = "I am Han";
        LocalDateTime createdAt = LocalDateTime.of(2024, 6, 14, 15, 47, 50);
        LocalDateTime modifiedAt = LocalDateTime.now();

        // when
        responseDto = new CommentResponseDto(msg,statusCode,id,username,comments,createdAt,modifiedAt);

        // then
        assertNotNull(responseDto);
        assertEquals(msg, responseDto.getMsg());
        assertEquals(statusCode, responseDto.getStatusCode());
        assertEquals(id, responseDto.getId());
        assertEquals(username, responseDto.getUsername());
        assertEquals(comments, responseDto.getComments());
        assertEquals(createdAt, responseDto.getCreatedAt());
        assertEquals(modifiedAt, responseDto.getModifiedAt());
    }

    @Test
    @DisplayName("CommentResponseDto toDto 메소드")
    void toDto() {
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
        comment.setUser(user);

        // when
        responseDto = CommentResponseDto.toDto(msg,statusCode,comment);

        // then
        assertNotNull(responseDto);
        assertEquals(msg, responseDto.getMsg());
        assertEquals(statusCode, responseDto.getStatusCode());
        assertEquals(comment.getId(), responseDto.getId());
        assertEquals(comment.getUser().getUsername(), responseDto.getUsername());
        assertEquals(comment.getComments(), responseDto.getComments());
        assertEquals(comment.getCreatedAt(), responseDto.getCreatedAt());
        assertEquals(comment.getModifiedAt(), responseDto.getModifiedAt());
    }

    @Test
    void toDeleteResponse() {
        // given
        String msg = "반환 메시지";
        int statusCode = 200;

        // when
        responseDto = CommentResponseDto.toDeleteResponse(msg,statusCode);

        // then
        assertNotNull(responseDto);
        assertEquals(msg, responseDto.getMsg());
        assertEquals(statusCode, responseDto.getStatusCode());
    }
}