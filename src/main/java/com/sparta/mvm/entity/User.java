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
    private Long USER_ID;

    @Column(name = "USERNAME", nullable = false)
    private String USERNAME;

    @Column(name = "PASSWORD", nullable = false)
    private String PASSWORD;

    @Column(name = "NAME", nullable = false)
    private String NAME;

    @Column(name = "EMAIL", nullable = false)
    private String EMAIL;

    @Column(name = "LINE_INTRO", nullable = false)
    private String LINE_INTRO;

    @Column(name = "USER_STATUS", nullable = false)
    private String USER_STATUS;

    @Column(name = "REFRESH_TOKEN", nullable = false)
    private String REFRESH_TOKEN;

    @Column(name = "USER_STATUS_TIME", nullable = false)
    private LocalDate USER_STATUS_TIME;

//    public void User(UserRequestDto userRequestDto){
//
//        this.loginId = userRequestDto.getLoginid();
//        this.password = userRequestDto.getPassword();
//        this.username = userRequestDto.getUsername();
//        this.email = userRequestDto.getEmail();
//        this.lineIntro = userRequestDto.getLineintro();
//
//    }

    @OneToMany(mappedBy = "User")
    private List<Post> post;

}
