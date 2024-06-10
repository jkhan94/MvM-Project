package com.sparta.mvm.controller;

import com.sparta.mvm.dto.PostRequestDto;
import com.sparta.mvm.dto.PostResponseDto;
import com.sparta.mvm.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 등록
    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> create(@Valid @RequestBody PostRequestDto request) {
        PostResponseDto post = postService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    // 게시글 전체 조회
    @GetMapping("/posts")
    public ResponseEntity<Map<String, Object>> getAll() {
        List<PostResponseDto> newsFeed = postService.getAll();
        if (newsFeed.isEmpty()) {
            // 뉴스피드가 비어있는 경우
            Map<String, Object> response = new HashMap<>();
            response.put("statusCode", HttpStatus.OK.value());
            response.put("msg", "먼저 작성하여 소식을 알려보세요 📝");
            return ResponseEntity.ok().body(response);
        } else {
            // 뉴스피드가 있는 경우
            return ResponseEntity.ok().body(Collections.singletonMap("newsFeed", newsFeed));
        }
    }

    // 게시글 부분 조회
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable(name = "postId") long postId) {
        return ResponseEntity.ok().body(postService.findById(postId));
    }

    // 게시글 수정
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDto> update(@PathVariable(name = "postId") long postId, @Valid @RequestBody PostRequestDto request) {
        return ResponseEntity.ok().body(postService.update(postId, request));
    }

    //게시글 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable(name = "postId") long postId) {
        postService.delete(postId);
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", HttpStatus.OK.value());
        response.put("msg", "게시글 삭제 성공 🎉");
        return ResponseEntity.ok().body(response);
    }
}
