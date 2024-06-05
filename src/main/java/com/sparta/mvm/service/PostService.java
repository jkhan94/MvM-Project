package com.sparta.mvm.service;

import com.sparta.mvm.dto.PostCreateRequest;
import com.sparta.mvm.dto.PostDeleteRequest;
import com.sparta.mvm.dto.PostResponse;
import com.sparta.mvm.dto.PostUpdateRequest;
import com.sparta.mvm.entity.Post;
import com.sparta.mvm.repository.PostRepository;
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

    public PostResponse findById(long postId) {
        Post post = findPostById(postId);
        return PostResponse.toDto(post, "게시글 조회 성공 🎉", 200);
    }

    private Post findPostById(long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글 고유번호를 찾을 수 없습니다."));
    }

    @Transactional
    public PostResponse save(PostCreateRequest request) {
        Post post = postRepository.save(request.toEntity());
        return PostResponse.toDto(post, "게시글 등록 성공 🎉", 200);
    }


    @Transactional
    public List<PostResponse> getAll() {
        List<Post> list = postRepository.findAll();
        return list
                .stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .map(post -> PostResponse.toDto(post, "게시글 조회 성공 🎉", 200))
                .toList();
    }

    @Transactional
    public PostResponse update(PostUpdateRequest request) {
        Post post = findPostById(request.getId());
        post.update(request.getContents());
        return PostResponse.toDto(post, "게시글 수정 성공 🎉", 200);
    }


    @Transactional
    public PostResponse delete(Long postId, PostDeleteRequest request) {
        Post post = findPostById(postId);
        postRepository.delete(post);
        return PostResponse.toDto(post, "게시글 삭제 성공 🎉", 200);
    }
}
