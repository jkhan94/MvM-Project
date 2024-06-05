package com.sparta.mvm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostUpdateRequest {
    private Long id;

    @NotBlank(message = "내용을 입력해 주세요")
    private String contents;

    public PostUpdateRequest(Long id,String contents) {
        this.id = id;
        this.contents = contents;
    }


}
