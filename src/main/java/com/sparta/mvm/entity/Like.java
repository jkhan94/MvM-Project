package com.sparta.mvm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "likes")
@NoArgsConstructor
public class Like extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }

    public Like(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }
}
