package com.sparta.mvm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.mvm.entity.Like;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class LikeResponseDto {
    private String msg;
    private int statusCode;
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    public static LikeResponseDto toDto(String msg, int statusCode, Like like) {
        return LikeResponseDto.builder()
                .msg(msg)
                .statusCode(statusCode)
                .id(like.getId())
                .createdAt(like.getCreatedAt())
                .modifiedAt(like.getModifiedAt())
                .build();
    }

    public static LikeResponseDto toDeleteResponse(String msg, int statusCode, Like like) {
        return LikeResponseDto.builder()
                .msg(msg)
                .statusCode(statusCode)
                .id(like.getId())
                .createdAt(like.getCreatedAt())
                .modifiedAt(like.getModifiedAt())
                .build();
    }

}
