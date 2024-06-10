package com.sparta.mvm.controller;

import com.sparta.mvm.dto.LikeResponseDto;
import com.sparta.mvm.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/posts/{postId}/likes")
    public ResponseEntity<LikeResponseDto> likePost(@PathVariable long postId)
    {
        return ResponseEntity.ok().body(likeService.likePost(postId));
    }

    @DeleteMapping("/posts/{postId}/likes")
    public ResponseEntity<LikeResponseDto> unlikePost(@PathVariable long postId)
    {
        return ResponseEntity.ok().body(likeService.unlikePost(postId));
    }

    @PostMapping("/comments/{commentId}/likes")
    public ResponseEntity<LikeResponseDto> likeComment(@PathVariable long commentId)
    {
       return ResponseEntity.ok().body(likeService.likeComment(commentId));
    }

    @DeleteMapping("/comments/{commentId}/likes")
    public ResponseEntity<LikeResponseDto> unlikeComment(@PathVariable long commentId)
    {
        return ResponseEntity.ok().body(likeService.unlikeComment(commentId));
    }
}
