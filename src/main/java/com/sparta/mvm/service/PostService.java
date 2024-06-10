package com.sparta.mvm.service;

import com.sparta.mvm.dto.PostRequestDto;
import com.sparta.mvm.dto.PostResponseDto;
import com.sparta.mvm.entity.Post;
import com.sparta.mvm.entity.User;
import com.sparta.mvm.exception.CustomException;
import com.sparta.mvm.exception.ErrorEnum;
import com.sparta.mvm.repository.PostRepository;
import com.sparta.mvm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponseDto findById(long postId) {
        Post post = findPostById(postId);
        return PostResponseDto.toDto("게시글 조회 성공 🎉", 200, post);
    }

    private Post findPostById(long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorEnum.BAD_POSTID));
    }

    @Transactional
    public PostResponseDto save(PostRequestDto request) {
        Long loggedInUserId = getLoggedInUserId();
        User user = getUserById(loggedInUserId);
        Post post = request.toEntity();
        post.setUser(user);
        post = postRepository.save(post);
        return PostResponseDto.toDto("게시글 등록 성공 🎉", 200, post);
    }

    @Transactional
    public List<PostResponseDto> getAll() {
        List<Post> list = postRepository.findAllByOrderByCreatedAtDesc();
        return list
                .stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .map(post -> PostResponseDto.toDto("게시글 조회 성공 🎉", 200, post))
                .toList();
    }

    @Transactional
    public PostResponseDto update(long postId, PostRequestDto request) {
        Post post = findPostById(postId);
        Long loggedInUserId = getLoggedInUserId();
        if (loggedInUserId.equals(post.getUser().getId())) {
            post.update(request.getContents());
            return PostResponseDto.toDto("게시글 수정 성공 🎉", 200, post);
        } else {
            throw new CustomException(ErrorEnum.BAD_AUTH_PUT);
        }
    }

    @Transactional
    public PostResponseDto delete(long postId) {
        Post post = findPostById(postId);
        Long loggedInUserId = getLoggedInUserId();
        if (loggedInUserId.equals(post.getUser().getId())) {
            postRepository.delete(post);
            return PostResponseDto.toDeleteResponse("게시글 삭제 성공 🎉", 200);
        } else {
            throw new CustomException(ErrorEnum.BAD_AUTH_DELETE);
        }
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorEnum.USER_NOT_FOUND));
    }

    // 현재 로그인한 사용자의 ID 가져오기
    private Long getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorEnum.USER_NOT_FOUND));
        return user.getId();
    }
}
