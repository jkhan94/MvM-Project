package com.sparta.mvm.service;

import com.sparta.mvm.dto.PostRequestDto;
import com.sparta.mvm.dto.PostResponseDto;
import com.sparta.mvm.entity.Post;
import com.sparta.mvm.entity.User;
import com.sparta.mvm.entity.UserStatusEnum;
import com.sparta.mvm.repository.PostRepository;
import com.sparta.mvm.repository.UserRepository;
import com.sparta.mvm.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    PostRepository postRepository;
    @Mock
    UserRepository userRepository;

    PostService postService;
    static User user;

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
        PostServiceTest.mockUserSetup();
    }

    @BeforeEach
    void setUp() {
        postService = new PostService(postRepository, userRepository);
    }

    @Test
    @DisplayName("아이디로 게시글 조회")
    void findById() {
        // given
        Long postId = 1L;
        Post post = new Post("I am contents");
        post.setUser(new User());
        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        // when
        PostResponseDto responseDto = postService.findById(postId);

        // then
        assertNotNull(responseDto);
        assertEquals("게시글 조회 성공 🎉", responseDto.getMsg());
        assertEquals(200, responseDto.getStatusCode());
        assertEquals(post.getId(), responseDto.getId());
        assertEquals(post.getUser().getUsername(), responseDto.getUsername());
        assertEquals(post.getContents(), responseDto.getContents());
        assertEquals(post.getCreatedAt(), responseDto.getCreatedAt());
        assertEquals(post.getModifiedAt(), responseDto.getModifiedAt());
    }

    @Test
    @DisplayName("게시글 등록 서비스")
    void save() {
        // given
        String contents = "I am content";
        PostRequestDto requestDto = new PostRequestDto(contents);
        Post post = requestDto.toEntity();
        post.setUser(user);
        Long userId = user.getId();
        String username = user.getUsername();

        given(userRepository.findByUsername(username)).willReturn(Optional.ofNullable(user));
        given(userRepository.findById(userId)).willReturn(Optional.ofNullable(user));
        given(postRepository.save(any())).willReturn(post);

        // when
        PostResponseDto responseDto = postService.save(requestDto);

        // then
        assertNotNull(responseDto);
        assertEquals("게시글 등록 성공 🎉", responseDto.getMsg());
        assertEquals(200, responseDto.getStatusCode());
        assertEquals(post.getId(), responseDto.getId());
        assertEquals(post.getUser().getUsername(), responseDto.getUsername());
        assertEquals(post.getContents(), responseDto.getContents());
        assertEquals(post.getCreatedAt(), responseDto.getCreatedAt());
        assertEquals(post.getModifiedAt(), responseDto.getModifiedAt());
    }

    // JPA Auditing 때문에 통합테스트에서 확인
    @Test
    @DisplayName("게시글 조회 서비스")
    void getAll() {
        // given
        String username = "hanid";
        String password = "#han56789";
        String name = "Han";
        String email = "han@example.com";
        String lineIntro = "It's Han";
        UserStatusEnum userStatus = UserStatusEnum.USER_NORMAL;
        User user = new User(username, password, name, email, lineIntro, userStatus);

        Post post1 = new Post("I am post1");
        Post post2 = new Post("I am post2");
        post1.setUser(user);
        post2.setUser(user);

        List<Post> list = List.of(
                post1, post2
        );

        postRepository.save(post1);
        post1.getCreatedAt();

        list.stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .map(post -> PostResponseDto.toDto("게시글 조회 성공 🎉", 200, post))
                .toList();

        given(postRepository.findAllByOrderByCreatedAtDesc()).willReturn(list);

        // when
        List<PostResponseDto> resultList = postService.getAll();

        // then
        assertNotNull(resultList);
        assertEquals("게시글 조회 성공 🎉", resultList.get(0).getMsg());
        assertEquals(200, resultList.get(0).getStatusCode());
        assertEquals(list.get(0), resultList.get(1));
        assertEquals(list.get(1), resultList.get(2));
    }

    @Test
    @DisplayName("게시글 수정 서비스")
    void update() {
        // given
        Long postId = 1L;
        String contents = "I am content";
        PostRequestDto requestDto = new PostRequestDto(contents);
        Post post = requestDto.toEntity();

        String username = "hanid";
        String password = "#han56789";
        String name = "Han";
        String email = "han@example.com";
        String lineIntro = "It's Han";
        UserStatusEnum userStatus = UserStatusEnum.USER_NORMAL;
        User user = new User(username, password, name, email, lineIntro, userStatus);
        post.setUser(user);

        Long userId = 1L;

        given(postRepository.findById(postId)).willReturn(Optional.of(post));
        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(postRepository.save(any())).willReturn(post);

        // when
        PostResponseDto responseDto = postService.update(postId, requestDto);

        // then
        assertNotNull(responseDto);
        assertEquals("게시글 수정 성공 🎉", responseDto.getMsg());
        assertEquals(200, responseDto.getStatusCode());
        assertEquals(post.getId(), responseDto.getId());
        assertEquals(post.getUser().getUsername(), responseDto.getUsername());
        assertEquals(post.getContents(), responseDto.getContents());
        assertEquals(post.getCreatedAt(), responseDto.getCreatedAt());
        assertEquals(post.getModifiedAt(), responseDto.getModifiedAt());
    }

    @Test
    @DisplayName("게시글 삭제 서비스")
    void deleteTest() {
        // given
        // when
        // then
    }
}