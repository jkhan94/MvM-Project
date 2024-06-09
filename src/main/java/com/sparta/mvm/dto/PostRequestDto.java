package com.sparta.mvm.dto;

import com.sparta.mvm.entity.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {
    @NotBlank(message = "내용을 입력해 주세요")
    private String contents;

    public PostRequestDto(String contents) {
        this.contents = contents;
    }

    public Post toEntity() {
        return new Post(this.contents);
    }
}
