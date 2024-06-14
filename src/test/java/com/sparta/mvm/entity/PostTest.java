package com.sparta.mvm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PostTest {
    Post post;

    @BeforeEach
    void setUp() {
        post = new Post();
    }

    @Test
    @DisplayName("Post 엔티티 생성")
    void createPostEntity() {
        // givem
        String contents = "나는 게시글입니다.";

        // when
        post = new Post(contents);

        // then
        assertNotNull(post);
        assertEquals(contents, post.getContents());
    }

    @Test
    @DisplayName("게시글 내용 수정")
    void update() {
        // given
        String contents = "나는 내용입니다";

        // when
        post.update(contents);

        // then
        assertNotNull(post);
        assertEquals(contents, post.getContents());
    }

    @Test
    @DisplayName("Post 엔티티의 setUser 메소드")
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
        post.setUser(user);

        // then
        assertNotNull(post.getUser());
        assertEquals(user, post.getUser());
    }

}