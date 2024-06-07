package com.sparta.mvm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequest {

    @NotBlank(message = "내용을 입력해 주세요")
    private String comments;

    public CommentUpdateRequest(String comments) {
        this.comments = comments;
    }

}
