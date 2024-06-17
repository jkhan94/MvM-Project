package com.sparta.mvm.service;

import com.sparta.mvm.config.SecurityConfig;
import com.sparta.mvm.dto.CommentRequestDto;
import com.sparta.mvm.dto.CommentResponseDto;
import com.sparta.mvm.entity.Comment;
import com.sparta.mvm.entity.Post;
import com.sparta.mvm.entity.User;
import com.sparta.mvm.entity.UserStatusEnum;
import com.sparta.mvm.repository.CommentRepository;
import com.sparta.mvm.repository.PostRepository;
import com.sparta.mvm.repository.UserRepository;
import com.sparta.mvm.security.UserDetailsImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CommentServiceIntegrationTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    SecurityConfig securityConfig;

    private CommentService commentService;
    private static User user;

    // 가짜 인증
    private static Principal mockPrincipal;

    // 가짜 유저와 가짜 인증 객체 생성
    private static void mockUserSetup() {
        // Mock 테스트 유져 생성
        String username = "hanid";
        String password = "#han56789";
        String name = "Han";
        String email = "han@example.com";
        String lineIntro = "It's Han";
        UserStatusEnum userStatus = UserStatusEnum.USER_NORMAL;
        user = new User(username, password, name, email, lineIntro, userStatus);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(user);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    @BeforeAll
    static void beforeAll() {
        CommentServiceIntegrationTest.mockUserSetup();
    }

    @BeforeEach
    void setUp() {
        commentService = new CommentService(commentRepository, postRepository, userRepository);
    }


    @Test
    @DisplayName("댓글 등록")
    void save() {
        // given
        Long postId = 1L;
        String contents = "I am content";
        Post post = new Post(contents);

        CommentRequestDto requestDto = new CommentRequestDto(contents);
        Comment comment = requestDto.toEntity(post);

        post.setUser(user);
        Long userId = user.getId();
        String username = user.getUsername();

        LocalDateTime now = LocalDateTime.now();

        given(postRepository.findById(postId)).willReturn(Optional.of(post));
        given(userRepository.findByUsername(username)).willReturn(Optional.ofNullable(user));
        given(userRepository.findById(userId)).willReturn(Optional.ofNullable(user));
        given(postRepository.save(any())).willReturn(post);

        // when
        CommentResponseDto responseDto = commentService.save(postId, requestDto);

        // then
        assertNotNull(responseDto);
        assertEquals("댓글 등록 성공 💌", responseDto.getMsg());
        assertEquals(200, responseDto.getStatusCode());
        assertEquals(comment.getId(), responseDto.getId());
        assertEquals(comment.getUser().getUsername(), responseDto.getUsername());
        assertEquals(comment.getComments(), responseDto.getComments());
        assertEquals(comment.getCreatedAt(), responseDto.getCreatedAt());
        assertEquals(comment.getModifiedAt(), responseDto.getModifiedAt());
    }

    @Test
    @DisplayName("댓글 수정")
    void update() {
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteTest() {
    }

    @Test
    @DisplayName("댓글 조회")
    void getAll() {
    }
}