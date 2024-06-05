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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostController {

    public final PostService service;

    // 게시글 등록
    @PostMapping("/post")
    public ResponseEntity<PostResponse> create(@Valid @RequestBody PostCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }

    // 게시글 전체 조회
    @GetMapping("/post")
    public ResponseEntity<Map<String, Object>> getAll() {
        List<PostResponse> newsFeed = service.getAll();
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
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostResponse> findById(@PathVariable(name = "postId") long postId) {
        return ResponseEntity.ok().body(service.findById(postId));
    }

    // 게시글 수정
    @PutMapping("/post/{postId}")
    public ResponseEntity<PostResponse> update(@PathVariable(name = "postId") long postId, @Valid @RequestBody PostUpdateRequest request){
        return ResponseEntity.ok().body(service.update(postId,request));
    }

    //게시글 삭제
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable(name = "postId") long postId) {
        service.delete(postId);
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", HttpStatus.OK.value());
        response.put("msg", "게시글 삭제 성공 🎉");
        return ResponseEntity.ok().body(response);
    }
}
