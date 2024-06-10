package com.sparta.mvm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.mvm.entity.Comment;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class CommentResponseDto {
    private String msg;
    private int statusCode;
    private Long id;
    private String username;
    @NotBlank(message = "내용을 입력해 주세요")
    private String comments;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    // 댓글 등록, 수정, 조회
    public static CommentResponseDto toDto(String msg, int statusCode, Comment comment) {
        return CommentResponseDto.builder()
                .msg(msg)
                .statusCode(statusCode)
                .id(comment.getId())
                .username(comment.getUser().getUsername())
                .comments(comment.getComments())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }

    // 댓글 삭제
    public static CommentResponseDto toDeleteResponse(String msg, int statusCode) {
        return CommentResponseDto.builder()
                .msg(msg)
                .statusCode(statusCode)
                .build();
    }
}
