package com.sparta.mvm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PostDeleteRequest {

    @NotNull
    private Long id;

    public PostDeleteRequest(Long id) {
        this.id = id;
    }
}
