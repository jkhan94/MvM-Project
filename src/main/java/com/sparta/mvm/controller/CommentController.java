package com.sparta.mvm.controller;

import com.sparta.mvm.dto.CommentCreateRequest;
import com.sparta.mvm.dto.CommentResponse;
import com.sparta.mvm.entity.User;
import com.sparta.mvm.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    public final CommentService service;

    // 댓글 등록
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> create(@PathVariable long postId, @Valid @RequestBody CommentCreateRequest request, @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(postId, request, user));
    }
}
