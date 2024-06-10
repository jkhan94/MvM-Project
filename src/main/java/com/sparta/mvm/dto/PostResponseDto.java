package com.sparta.mvm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.mvm.entity.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PostResponseDto {
    private String msg;
    private int statusCode;
    private Long id;
    @NotBlank(message = "이름을 입력해 주세요")
    private String username;
    @NotBlank(message = "내용을 입력해 주세요")
    private String contents;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    //게시글 등록, 수정, 조회
    public static PostResponseDto toDto(String msg, int statusCode, Post post) {
        return PostResponseDto.builder()
                .msg(msg)
                .statusCode(statusCode)
                .id(post.getId())
                .username(post.getUser().getUsername())
                .contents(post.getContents())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }

    // 게시글 삭제
    public static PostResponseDto toDeleteResponse(String msg, int statusCode) {
        return PostResponseDto.builder()
                .msg(msg)
                .statusCode(statusCode)
                .build();
    }
}
