package com.sparta.mvm.controller;

import com.sparta.mvm.entity.TestUser;
import com.sparta.mvm.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class AuthTestContoller {

    @GetMapping("/test")
    public ResponseEntity<TestUser> test(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        TestUser testUser = new TestUser();
        testUser.setPassword(userDetails.getPassword());
        testUser.setLoginId(userDetails.getUsername());
        return ResponseEntity.ok().body(testUser);
    }
}
