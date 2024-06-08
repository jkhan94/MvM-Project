package com.sparta.mvm.controller;

import com.sparta.mvm.dto.CommentRequestDto;
import com.sparta.mvm.dto.CommentResponseDto;
import com.sparta.mvm.service.CommentService;
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
public class CommentController {

    public final CommentService service;

    // 댓글 등록
//    @PostMapping("/posts/{postId}/comments")
//    public ResponseEntity<CommentResponse> create(@PathVariable long postId, @Valid @RequestBody CommentCreateRequest request, @AuthenticationPrincipal User user) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(postId, request, user));
//    }

    // 댓글 등록
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> create(@PathVariable(name = "postId") long postId, @Valid @RequestBody CommentRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(postId, request));
    }

    // 댓글 조회
    @GetMapping("/comments")
    public ResponseEntity<Map<String,Object>> getAll(){
        List<CommentResponseDto> newFeed_Comment = service.getAll();
        if(newFeed_Comment.isEmpty())
        {
            Map<String,Object> response = new HashMap<>();
            response.put("statusCode", HttpStatus.OK.value());
            response.put("msg", "먼저 댓글을 작성해 보세요 📝");
            return ResponseEntity.ok().body(response);
        }
        else{
            return ResponseEntity.ok().body(Collections.singletonMap("newFeed_Comment", newFeed_Comment));
        }
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> update(@PathVariable(name = "commentId") long commentId, @Valid @RequestBody CommentRequestDto request) {
        return ResponseEntity.ok().body(service.update(commentId,request));
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable(name = "commentId") long commentId) {
        service.delete(commentId);
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", HttpStatus.OK.value());
        response.put("msg", "댓글 삭제 성공 🎉");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
