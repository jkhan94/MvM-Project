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
public class CommentResponse {
    private String msg;
    private int statusCode;
    private Long id;
    @NotBlank(message = "내용을 입력해 주세요")
    private String comments;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    public CommentResponse(String msg, int statusCode, Long id, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.msg = msg;
        this.statusCode = statusCode;
        this.id = id;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.comments = comment.getComments();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }

    // 댓글 등록
    public static CommentResponse toDto(String msg, int statusCode, Comment comment) {
        return CommentResponse.builder()
                .msg(msg)
                .statusCode(statusCode)
                .comments(comment.getComments())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }

    // 댓글 삭제
    public static CommentResponse toDeleteResponse(String msg, int statusCode) {
        return CommentResponse.builder()
                .msg(msg)
                .statusCode(statusCode)
                .build();
    }

    // 댓글 조회
    public static CommentResponse toList(String msg, int statusCode, Comment comment) {
        return CommentResponse.builder()
                .msg(msg)
                .statusCode(statusCode)
                .id(comment.getId())
                .comments(comment.getComments())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
