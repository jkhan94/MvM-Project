package com.sparta.mvm.service;

import com.sparta.mvm.dto.PostCreateRequest;
import com.sparta.mvm.dto.PostResponse;
import com.sparta.mvm.entity.Post;
import com.sparta.mvm.entity.User;
import com.sparta.mvm.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponse save(PostCreateRequest request) {
        Post post = postRepository.save(request.toEntity());
        return PostResponse.toDto(post);
    }
}
