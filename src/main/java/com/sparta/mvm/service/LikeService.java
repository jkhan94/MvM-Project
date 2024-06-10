package com.sparta.mvm.service;

import com.sparta.mvm.dto.LikeResponseDto;
import com.sparta.mvm.entity.Comment;
import com.sparta.mvm.entity.Like;
import com.sparta.mvm.entity.Post;
import com.sparta.mvm.entity.User;
import com.sparta.mvm.exception.CustomException;
import com.sparta.mvm.exception.ErrorEnum;
import com.sparta.mvm.repository.CommentRepository;
import com.sparta.mvm.repository.LikeRepository;
import com.sparta.mvm.repository.PostRepository;
import com.sparta.mvm.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public LikeService(LikeRepository likeRepository, PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorEnum.BAD_POSTID));
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorEnum.BAD_COMMENTID));
    }

    @Transactional
    public LikeResponseDto likePost(Long postId) {
        User user = getLoggedInUser();
        Post post = findPostById(postId);
        if (user.equals(post.getUser())) {
            throw new CustomException(ErrorEnum.CANNOT_LIKE_OWN_POST);
        }
        if (likeRepository.existsByUserAndPost(user, post)) {
            throw new CustomException(ErrorEnum.DUPLICATE_LIKE);
        }
        Like like = new Like(user, post);
        likeRepository.save(like);
        return LikeResponseDto.toDto("게시물 좋아요 성공 🎉", 200, like);
    }

    @Transactional
    public LikeResponseDto unlikePost(Long postId) {
        User user = getLoggedInUser();
        Post post = findPostById(postId);
        Like like = likeRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new CustomException(ErrorEnum.LIKE_NOT_FOUND));
        likeRepository.delete(like);
        return LikeResponseDto.toDeleteResponse("게시물 좋아요 취소 성공 🎉", 200, like);
    }

    @Transactional
    public LikeResponseDto likeComment(Long commentId) {
        User user = getLoggedInUser();
        Comment comment = findCommentById(commentId);
        if (user.equals(comment.getUser())) {
            throw new CustomException(ErrorEnum.CANNOT_LIKE_OWN_COMMENT);
        }
        if (likeRepository.existsByUserAndComment(user, comment)) {
            throw new CustomException(ErrorEnum.DUPLICATE_LIKE);
        }
        Like like = new Like(user, comment);
        likeRepository.save(like);
        return LikeResponseDto.toDto("댓글 좋아요 성공 🎉", 200, like);
    }

    @Transactional
    public LikeResponseDto unlikeComment(Long commentId) {
        User user = getLoggedInUser();
        Comment comment = findCommentById(commentId);
        Like like = likeRepository.findByUserAndComment(user, comment)
                .orElseThrow(() -> new CustomException(ErrorEnum.LIKE_NOT_FOUND));
        likeRepository.delete(like);
        return LikeResponseDto.toDeleteResponse("댓글 좋아요 취소 성공 🎉", 200, like);
    }

    // 현재 로그인한 사용자의 ID 가져오기
    private User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorEnum.USER_NOT_FOUND));
    }
}
