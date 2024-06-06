package com.sparta.mvm.AuthTest;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class TestUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERNAME", nullable = false, length = 20)
    private String username;

    @Column(name = "PASSWORD", nullable = false, length = 255)
    private String password;

    @Column(name = "REFRESH_TOKEN", nullable = true, length = 255)
    private String refreshToken;

    public TestUser(String name, String password) {
        this.username = name;
        this.password = password;
    }
}
