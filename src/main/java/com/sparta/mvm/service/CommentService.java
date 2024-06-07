package com.sparta.mvm.service;

import com.sparta.mvm.dto.CommentCreateRequest;
import com.sparta.mvm.dto.CommentResponse;
import com.sparta.mvm.dto.PostResponse;
import com.sparta.mvm.entity.Comment;
import com.sparta.mvm.entity.Post;
import com.sparta.mvm.entity.User;
import com.sparta.mvm.repository.CommentRepository;
import com.sparta.mvm.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    private Post findPostById(long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글 고유번호를 찾을 수 없습니다."));
    }

    @Transactional
    public CommentResponse save(Long postId, CommentCreateRequest request, User user) {
        Post post = findPostById(postId);
        Comment comment = commentRepository.save(new Comment(request, post, user));
        return CommentResponse.toDto("댓글 등록 성공 💌", 200, comment);
    }
}
