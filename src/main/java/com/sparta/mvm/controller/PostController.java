package com.sparta.mvm.controller;

import com.sparta.mvm.dto.PostCreateRequest;
import com.sparta.mvm.dto.PostResponse;
import com.sparta.mvm.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    public final PostService service;
    @PostMapping("/post")
    public ResponseEntity<PostResponse> create(
            @Valid @RequestBody PostCreateRequest request){
        // 게시글 등록
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }

}
