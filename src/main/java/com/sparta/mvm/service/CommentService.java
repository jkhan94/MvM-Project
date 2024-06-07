package com.sparta.mvm.service;

import com.sparta.mvm.dto.CommentCreateRequest;
import com.sparta.mvm.dto.CommentResponse;
import com.sparta.mvm.dto.CommentUpdateRequest;
import com.sparta.mvm.entity.Comment;
import com.sparta.mvm.entity.Post;
import com.sparta.mvm.repository.CommentRepository;
import com.sparta.mvm.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    private Post findPostById(long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글 고유번호를 찾을 수 없습니다."));
    }

    private Comment findCommentById(long commentId) {
        return commentRepository.findById(commentId).orElseThrow(()-> new IllegalArgumentException("댓글 고유번호를 찾을 수 없습니다."));
    }

//    public CommentResponse save(Long postId, CommentCreateRequest request, User user) {
//        Post post = findPostById(postId);
//        Comment comment = commentRepository.save(new Comment(request, post, user));
//        return CommentResponse.toDto("댓글 등록 성공 💌", 200, comment);
//    }

    @Transactional
    public CommentResponse save(long postId, CommentCreateRequest request) {
        Post post = findPostById(postId);
        Comment comment = commentRepository.save(request.toEntity(post)); // toEntity 메서드 사용
        return CommentResponse.toDto("댓글 등록 성공 💌", 200, comment);
    }

    @Transactional
    public CommentResponse update(long commentId, CommentUpdateRequest request) {
        Comment comment = findCommentById(commentId);
        comment.update(request.getComments());
        return CommentResponse.toDto("댓글 수정 성공 🎉",200,comment);

    }

    @Transactional
    public CommentResponse delete(long commentId) {
        Comment comment = findCommentById(commentId);
        commentRepository.delete(comment);
        return CommentResponse.toDeleteResponse("댓글 삭제 성공 🎉",200);
    }

    public List<CommentResponse> getAll() {
        List<Comment> list = commentRepository.findAllByOrderByCreatedAtDesc();
        return list
                .stream()
                .sorted(Comparator.comparing(Comment::getCreatedAt).reversed())
                .map(comment -> CommentResponse.toList("댓글 조회 성공 🎉",200,comment))
                .toList();
    }
}
