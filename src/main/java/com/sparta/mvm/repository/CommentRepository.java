package com.sparta.mvm.repository;

import com.sparta.mvm.entity.Comment;
import com.sparta.mvm.entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAllByOrderByCreatedAtDesc();
}
