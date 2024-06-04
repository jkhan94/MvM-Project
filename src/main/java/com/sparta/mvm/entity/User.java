package com.sparta.mvm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    @Column(name = "PASSWORD", nullable = false, length = 30)
    private String password;

    @Column(name = "NAME", nullable = false, length = 30)
    private String name;

    @Column(name = "EMAIL", nullable = false, length = 31)
    private String email;

    @Column(name = "LINE_INTRO", length = 255)
    private String lineIntro;

    @Column(name = "USER_STATUS", nullable = false, length = 2)
    private String userStatus;

    @Column(name = "REFRESH_TOKEN", nullable = false, length = 255)
    private String refreshToken;

    @Column(name = "USER_STATUS_TIME", nullable = false)
    private LocalDate userStatusTime;


/*    public void User(UserRequestDto userRequestDto){

        this.loginId = userRequestDto.getLoginid();
        this.password = userRequestDto.getPassword();
        this.username = userRequestDto.getUsername();
        this.email = userRequestDto.getEmail();
        this.lineIntro = userRequestDto.getLineintro();

    }*/

    @OneToMany(mappedBy = "user")
    private List<Post> post;

}
