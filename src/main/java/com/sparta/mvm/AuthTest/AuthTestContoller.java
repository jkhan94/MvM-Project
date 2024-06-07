package com.sparta.mvm.AuthTest;

import com.sparta.mvm.exception.CommonResponse;
import com.sparta.mvm.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class AuthTestContoller {

    private final AuthService authService;

    @GetMapping("/init")
    public ResponseEntity<CommonResponse<Void>> test() {
        authService.initTable();

        return ResponseEntity.ok().body(CommonResponse.<Void>builder()
                .msg("초기화API성공")
                .build());
    }

    @GetMapping("/test")
    public ResponseEntity<CommonResponse<TestUser>> tokenReissuance(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        TestUser testUser = new TestUser();
        testUser.setUsername(userDetails.getUsername());
        testUser.setPassword(userDetails.getPassword());
        return ResponseEntity.ok().body(CommonResponse.<TestUser>builder()
                .data(testUser)
                .msg("테스트 API 성공")
                .build());
    }
}
