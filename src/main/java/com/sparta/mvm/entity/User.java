//package com.sparta.mvm.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Entity
//@Getter
//@Table(name = "metromusic")
//@NoArgsConstructor
//public class User extends Timestamped {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long userId;
//
//    @Column(name = "loginId", nullable = false)
//    private String loginId;
//
//    @Column(name = "password", nullable = false)
//    private String password;
//
//    @Column(name = "username", nullable = false)
//    private String username;
//
//    @Column(name = "email", nullable = false)
//    private String email;
//
//    @Column(name = "lineIntro", nullable = false)
//    private String lineIntro;
//
//    @Column(name = "userStatus", nullable = false)
//    private String userStatus;
//
//    @Column(name = "refreshToken", nullable = false)
//    private String refreshToken;
//
//    @Column(name = "userStatusTime", nullable = false)
//    private LocalDate userStatusTime;
//
////    public void User(UserRequestDto userRequestDto){
////
////        this.loginId = userRequestDto.getLoginid();
////        this.password = userRequestDto.getPassword();
////        this.username = userRequestDto.getUsername();
////        this.email = userRequestDto.getEmail();
////        this.lineIntro = userRequestDto.getLineintro();
////
////    }
//
////    @OneToMany(mappedBy = "User")
////    private List<Post> post;
//
//}
