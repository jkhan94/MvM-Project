package com.sparta.mvm.entity;

import com.sparta.mvm.dto.ProfileRequestDto;
import com.sparta.mvm.dto.SignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "metromusic")
@NoArgsConstructor
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERNAME", nullable = false, length = 20)
    private String username;

    @Column(name = "PASSWORD", nullable = false, length = 65)
    private String password;

    @Column(name = "NAME", nullable = false, length = 30)
    private String name;

    @Column(name = "EMAIL", nullable = false, length = 31)
    private String email;

    @Column(name = "LINE_INTRO", length = 255)
    private String lineIntro;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatusEnum userStatus;

    @Column(name = "REFRESH_TOKEN", length = 255)
    private String refreshToken;

    @CreatedDate
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "USER_STATUS_TIME", nullable = false)
    private LocalDateTime userStatusTime;

    public User(SignupRequestDto signupRequestDto) {
        this.username = signupRequestDto.getUsername();
        this.password = signupRequestDto.getPassword();
        this.name = signupRequestDto.getName();
        this.email = signupRequestDto.getEmail();
        this.lineIntro = signupRequestDto.getLineIntro();
        this.userStatus = UserStatusEnum.USER_NORMAL;
    }

    @OneToMany(mappedBy = "user")
    private List<Post> post;

    public User(String username, String password, String name, String email, String lineIntro, UserStatusEnum userStatusEnum) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.lineIntro = lineIntro;
        this.userStatus = UserStatusEnum.USER_NORMAL;
    }

    public void update(ProfileRequestDto requestDto) {
        this.name = requestDto.getName();
        this.lineIntro = requestDto.getLineIntro();
        this.password = requestDto.getChangedPassword();
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void resignStatus() {
        this.userStatus = UserStatusEnum.USER_RESIGN;
    }
}