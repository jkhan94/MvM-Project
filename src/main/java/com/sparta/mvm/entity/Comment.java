package com.sparta.mvm.entity;

import com.sparta.mvm.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "comments")
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "COMMENTS",nullable = false)
    private String comments;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;


    public Comment(String comments, Post post, User user) {
        this.comments = comments;
        this.post = post;
   //     this.user = user;
    }
    public Comment(CommentRequestDto commentCreateRequest, Post post) {
        this.comments = commentCreateRequest.getComments();
        this.post = post;
    }
    public Comment(CommentRequestDto request, Post post, User user) {
        this.comments = request.getComments();
        this.post = post;
     //   this.user = user;
    }

    public Comment(String comments)
    {
        this.comments = comments;
    }

    public Comment(String comments, Post post)
    {
        this.comments = comments;
        this.post = post;
    }

    public void update(String comments) {
        this.comments = comments;
    }
}
