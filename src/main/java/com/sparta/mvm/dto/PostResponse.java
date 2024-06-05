package com.sparta.mvm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.mvm.entity.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
public class PostResponse {
    private Long postId;
    @NotBlank(message = "내용을 입력해 주세요")
    private String contents;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;
    private int statusCode;
    private String msg;


    public PostResponse(Long postId,  String contents, LocalDateTime createdAt, LocalDateTime modifiedAt, int statusCode, String msg) {
        this.postId = postId;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.statusCode = statusCode;
        this.msg = msg;
    }

    public PostResponse(Post post) {
        this.postId = post.getId();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }

    // 게시글 조회
    public static PostResponse toList(Post post, String msg, int statusCode) {
        return PostResponse.builder()
                .msg(msg)
                .statusCode(statusCode)
                .postId(post.getId())
           //   .username(post.getUser().getUsername())
                .contents(post.getContents())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }

    //게시글 등록, 수정
    public static PostResponse toDto(Post post, String msg, int statusCode) {
        return PostResponse.builder()
                .msg(msg)
                .statusCode(statusCode)
           //   .username(post.getUser().getUsername())
                .contents(post.getContents())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }

    // 게시글 삭제
    public static PostResponse toDeleteResponse(Post post, String msg, int statusCode) {
        return PostResponse.builder()
                .msg(msg)
                .statusCode(statusCode)
                .build();
    }
}
