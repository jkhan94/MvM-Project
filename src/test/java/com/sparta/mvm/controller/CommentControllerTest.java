package com.sparta.mvm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.mvm.config.SecurityConfig;
import com.sparta.mvm.dto.CommentRequestDto;
import com.sparta.mvm.dto.CommentResponseDto;
import com.sparta.mvm.entity.User;
import com.sparta.mvm.entity.UserStatusEnum;
import com.sparta.mvm.repository.CommentRepository;
import com.sparta.mvm.security.UserDetailsImpl;
import com.sparta.mvm.service.CommentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = {CommentController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = SecurityConfig.class
                )
        }
)
class CommentControllerTest {

    private MockMvc mvc;

    // 가짜 인증
    private static Principal mockPrincipal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    // 컨트롤러에서 주입받는 가짜 빈
    @MockBean
    CommentService commentService;

    @MockBean
    CommentRepository commentRepository;

    // MockMvc에 객체 주입
    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                // 만들어준 가짜 필터 적용
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    // 가짜 유저와 가짜 인증 객체 생성
    private static void mockUserSetup() {
        // Mock 테스트 유져 생성
        String username = "hanid";
        String password = "#han56789";
        String name = "Han";
        String email = "han@example.com";
        String lineIntro = "It's Han";
        UserStatusEnum userStatus = UserStatusEnum.USER_NORMAL;
        User user = new User(username, password, name, email, lineIntro, userStatus);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(user);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
    }


    @BeforeAll
    static void beforeAll() {
        CommentControllerTest.mockUserSetup();
    }

    @Test
    @DisplayName("댓글 validation test")
    void validationTest() throws Exception {
        // given
        Long postId = 1L;
        String comments = "";
        CommentRequestDto requestDto = new CommentRequestDto(comments);
        String commentInfo = objectMapper.writeValueAsString(requestDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/posts/{postId}/comments", postId)
                .content(commentInfo)
                .contentType(MediaType.APPLICATION_JSON);

        // when - then
        mvc.perform(requestBuilder)
                .andDo(print());

    }

    @Test
    @DisplayName("댓글 등록")
    void create() throws Exception {
        // given
        Long postId = 1L;
        String comments = "I am content";
        CommentRequestDto requestDto = new CommentRequestDto(comments);
        String postInfo = objectMapper.writeValueAsString(requestDto);

        // when - then
        mvc.perform(post("/posts/{postId}/comments", postId)
                        .content(postInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 전체 조회")
    void getAll() throws Exception {
        // given
        List<CommentResponseDto> list = List.of(
                new CommentResponseDto("a", 200, 1L, "HanID", "Han", LocalDateTime.now(), LocalDateTime.now())
                , new CommentResponseDto("b", 200, 2L, "HanID2", "Han2", LocalDateTime.now(), LocalDateTime.now())
        );
        Mockito.when(commentService.getAll()).thenReturn(list);

        // when - then
        mvc.perform(get("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("댓글 수정")
    void update() throws Exception {
        // given
        Long commentId = 1L;
        String comments = "I am content";
        CommentRequestDto requestDto = new CommentRequestDto(comments);
        String commentInfo = objectMapper.writeValueAsString(requestDto);

        // when - then
        mvc.perform(put("/comments/{commentId}", commentId)
                        .content(commentInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteTest() throws Exception {
        // given
        Long commentId = 1L;

        // when - then
        mvc.perform(delete("/comments/{commentId}", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}