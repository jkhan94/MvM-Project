package com.sparta.mvm.service;

import com.sparta.mvm.dto.CommentRequestDto;
import com.sparta.mvm.dto.CommentResponseDto;
import com.sparta.mvm.entity.Comment;
import com.sparta.mvm.entity.Post;
import com.sparta.mvm.exception.CustomException;
import com.sparta.mvm.exception.ErrorEnum;
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
        return postRepository.findById(postId).orElseThrow(() ->  new CustomException(ErrorEnum.BAD_POSTID));
    }

    private Comment findCommentById(long commentId) {
        return commentRepository.findById(commentId).orElseThrow(()-> new CustomException(ErrorEnum.BAD_COMMENTID));
    }

//    public CommentResponse save(Long postId, CommentCreateRequest request, User user) {
//        Post post = findPostById(postId);
//        Comment comment = commentRepository.save(new Comment(request, post, user));
//        return CommentResponse.toDto("댓글 등록 성공 💌", 200, comment);
//    }

    @Transactional
    public CommentResponseDto save(long postId, CommentRequestDto request) {
        Post post = findPostById(postId);
        Comment comment = commentRepository.save(request.toEntity(post)); // toEntity 메서드 사용
        return CommentResponseDto.toDto("댓글 등록 성공 💌", 200, comment);
    }

    @Transactional
    public CommentResponseDto update(long commentId, CommentRequestDto  request) {
        Comment comment = findCommentById(commentId);
        comment.update(request.getComments());
        return CommentResponseDto.toDto("댓글 수정 성공 🎉",200,comment);

    }

    @Transactional
    public CommentResponseDto delete(long commentId) {
        Comment comment = findCommentById(commentId);
        commentRepository.delete(comment);
        return CommentResponseDto.toDeleteResponse("댓글 삭제 성공 🎉",200);
    }

    public List<CommentResponseDto> getAll() {
        List<Comment> list = commentRepository.findAllByOrderByCreatedAtDesc();
        return list
                .stream()
                .sorted(Comparator.comparing(Comment::getCreatedAt).reversed())
                .map(comment -> CommentResponseDto.toList("댓글 조회 성공 🎉",200,comment))
                .toList();
    }
}
