package com.sparta.mvm.entity;

import com.sparta.mvm.dto.ProfileRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
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

    @Column(name = "USER_STATUS", nullable = false, length = 100)
    private String userStatus;

    @Column(name = "REFRESH_TOKEN", length = 255)
    private String refreshToken;

    @CreatedDate
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "USER_STATUS_TIME", nullable = false)
    private LocalDateTime userStatusTime;

    public User(String username, String password, String name, String email, String lineIntro, String userStatus) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.lineIntro = lineIntro;
        this.userStatus = userStatus;
    }

    @OneToMany(mappedBy = "user")
    private List<Post> post;

    public void update(ProfileRequestDto requestDto) {
        this.name = requestDto.getName();
        this.lineIntro = requestDto.getLineIntro();
        this.password = requestDto.getChangedPassword();
    }

    public void setRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void resignStatus() {
        this.userStatus = String.valueOf(UserStatusEnum.USER_RESIGN);
    }
}