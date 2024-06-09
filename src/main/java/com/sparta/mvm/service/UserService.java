package com.sparta.mvm.service;

import ch.qos.logback.core.spi.ErrorCodes;
import com.sparta.mvm.dto.ResignDto;
import com.sparta.mvm.dto.SignupRequestDto;
import com.sparta.mvm.dto.SignupResponseDto;
import com.sparta.mvm.entity.User;
import com.sparta.mvm.entity.UserStatusEnum;
import com.sparta.mvm.exception.CustomException;
import com.sparta.mvm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.mvm.exception.ErrorEnum.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public SignupResponseDto signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String name = requestDto.getName();
        String lineIntro = requestDto.getLineIntro();
        //String userStatus = UserStatusEnum.USER_NORMAL.name();
        UserStatusEnum userStatusEnum = UserStatusEnum.USER_NORMAL;

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 등록
        User user = new User(username, password, name, email, lineIntro, userStatusEnum);
        userRepository.save(user);

        return new SignupResponseDto(user);
    }

    @Transactional
    public void resign(User user, ResignDto resignDto) {
        User userRep = userRepository.findByUsername(user.getUsername()).orElseThrow();
        if (!passwordEncoder.matches(resignDto.getPassword(), userRep.getPassword())) {
            throw new CustomException(BAD_PASSWORD);
        }
        if (userRep.getUserStatus().equals(UserStatusEnum.USER_RESIGN)) {
            throw new CustomException(BAD_RESIGN);
        }

        userRep.resignStatus();
    }
}
