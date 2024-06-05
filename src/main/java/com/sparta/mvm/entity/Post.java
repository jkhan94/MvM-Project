//package com.sparta.mvm.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Getter
//@Table(name = "post")
//@NoArgsConstructor
//public class Post extends Timestamped{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "contents",nullable = false)
//    private String contents;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id",nullable = false)
//    private User user;
//
//    public Post(String contents, User user) {
//        this.contents = contents;
//        this.user = user;
//    }
//}
