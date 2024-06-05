package com.sparta.mvm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.mvm.entity.Post;

import java.time.LocalDateTime;

public class PostResponse {
    private Long id;
    private String contents;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    public PostResponse(Long id, String contents,LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
    public static PostResponse toDto(Post post)
    {
        return new PostResponse(post.getId(),
                post.getContents(),
                post.getCreatedAt(),
                post.getModifiedAt()
                );
    }

}
