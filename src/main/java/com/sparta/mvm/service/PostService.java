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

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long userId, PostRequestDto request) {
        User user = getUserById(userId);
        PostResponseDto responseDto = new PostResponseDto();
        return responseDto;
    }

    public PostResponseDto findById(long postId) {
        Post post = findPostById(postId);
        return PostResponseDto.toList("게시글 조회 성공 🎉", 200, post);
    }

    private Post findPostById(long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorEnum.BAD_POSTID));
    }

    @Transactional
    public PostResponseDto save(long userId, PostRequestDto request) {
        User user = getUserById(userId);
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
                .map(post -> PostResponseDto.toList("게시글 조회 성공 🎉", 200, post))
                .toList();
    }

    @Transactional
    public PostResponseDto update( long postId, PostRequestDto request) {
        Post post = findPostById(postId);
        post.update(request.getContents());
        return PostResponseDto.toDto("게시글 수정 성공 🎉", 200, post);
    }

    @Transactional
    public PostResponseDto delete(long postId) {
        Post post = findPostById(postId);
        postRepository.delete(post);
        return PostResponseDto.toDeleteResponse("게시글 삭제 성공 🎉", 200);
    }

    private User getUserById(Long userId)
    {
        return userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorEnum.USER_NOT_FOUND));
    }

}
