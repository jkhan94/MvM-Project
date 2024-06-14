package com.sparta.mvm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {
    Comment comment;

    @BeforeEach
    void setUp() {
        comment=new Comment();
    }

    @Test
    @DisplayName("Comment 생성")
    void createComment(){
        // given
        String comments = "나는 댓글입니다";
        String contents = "나는 게시글입니다.";
        Post post = new Post(contents);

        // when
        comment=new Comment(comments, post);

        // then
        assertNotNull(comment);
        assertEquals(comments, comment.getComments());
        assertEquals(post, comment.getPost());

    }

    @Test
    @DisplayName("댓글 내용 수정")
    void update() {
        // given
        String comments = "나는 댓글입니다";

        // when
        comment.update(comments);

        // then
        assertEquals(comments, comment.getComments());
    }


    @Test
    @DisplayName("Comment의 setUser 메소드")
    void setUser() {
        // given
        String username = "hanid";
        String password = "#han56789";
        String name = "Han";
        String email = "han@example.com";
        String lineIntro = "It's Han";
        UserStatusEnum userStatus = UserStatusEnum.USER_NORMAL;
        User user = new User(username, password, name, email, lineIntro, userStatus);

        // when
        comment.setUser(user);

        // then
        assertNotNull(comment.getUser());
        assertEquals(user, comment.getUser());
    }

}