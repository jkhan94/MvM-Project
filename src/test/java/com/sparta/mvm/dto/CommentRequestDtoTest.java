package com.sparta.mvm.dto;

import com.sparta.mvm.entity.Comment;
import com.sparta.mvm.entity.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CommentRequestDtoTest {
    CommentRequestDto requestDto;

    @Test
    @DisplayName("dto 생성")
    void createRequestDto() {
        // given
        String comments = "나는 내용입니다";

        // when
        requestDto = new CommentRequestDto(comments);

        // then
        assertNotNull(requestDto);
        assertEquals(comments, requestDto.getComments());
    }

    @Test
    @DisplayName("toEntity 메소드")
    void toEntity() {
        // given
        String comments = "나는 내용입니다";
        String contents = "나는 게시글입니다";
        Post post = new Post(contents);
        requestDto = new CommentRequestDto(comments);

        // when
        Comment comment = requestDto.toEntity(post);

        // then
        assertNotNull(comment);
        assertEquals(requestDto.getComments(), comment.getComments());
        assertEquals(post, comment.getPost());
    }
}