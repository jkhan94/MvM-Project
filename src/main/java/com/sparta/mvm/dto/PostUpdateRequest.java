package com.sparta.mvm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequest {

    @NotBlank(message = "내용을 입력해 주세요")
    private String contents;

    public PostUpdateRequest(String contents) {
        this.contents = contents;
    }

}
