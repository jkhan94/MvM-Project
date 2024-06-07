package com.sparta.mvm.dto;

import com.sparta.mvm.entity.Comment;
import com.sparta.mvm.entity.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateRequest {
    @NotBlank(message = "내용을 입력해 주세요")
    private String comments;

    public CommentCreateRequest(String comments) {
        this.comments = comments;
    }

    public Comment toEntity(Post post) {
        return new Comment(this.comments, post);
    }

}
