package com.sparta.mvm.controller;

import com.sparta.mvm.dto.PostCreateRequest;
import com.sparta.mvm.dto.PostDeleteRequest;
import com.sparta.mvm.dto.PostResponse;
import com.sparta.mvm.dto.PostUpdateRequest;
import com.sparta.mvm.entity.Post;
import com.sparta.mvm.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    public final PostService service;

    // 게시글 등록
    @PostMapping("/post")
    public ResponseEntity<PostResponse> create(
            @Valid @RequestBody PostCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }

    // 게시글 전체 조회
    @GetMapping("/post")
    public ResponseEntity<List<PostResponse>> getAll(){
        return ResponseEntity.ok()
                .body(service.getAll());
    }

    // 게시글 부분 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostResponse> findById(@PathVariable(name = "postId") long postId) {
        return ResponseEntity.ok()
                .body(service.findById(postId));
    }

    // 게시글 수정
    @PutMapping("/post/{postId}")
    public ResponseEntity<PostResponse> update( @Valid @RequestBody PostUpdateRequest request){
        return ResponseEntity.ok().body(service.update(request));
    }

    //게시글 삭제
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<PostResponse> delete(@PathVariable Long postId, @Valid @RequestBody PostDeleteRequest request){
        return ResponseEntity.ok()
                .body(service.delete(postId, request));
    }

}
