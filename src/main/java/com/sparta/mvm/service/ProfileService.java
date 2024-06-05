package com.sparta.mvm.service;

import com.sparta.mvm.dto.ProfileRequestDto;
import com.sparta.mvm.dto.ProfileResponseDto;
import com.sparta.mvm.entity.User;
import com.sparta.mvm.exception.CustomException;
import com.sparta.mvm.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.mvm.exception.ErrorEnum.*;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileResponseDto getProfile(Long userId) {
        User user = getUserById(userId);
        ProfileResponseDto responseDto = new ProfileResponseDto(user);

        return responseDto;
    }

    @Transactional
    public ProfileResponseDto updateProfile(Long userId, ProfileRequestDto requestDto) {
        User user = getUserById(userId);

        // 비밀번호를 바꿀 때
        if (!requestDto.getChangedPassword().isEmpty()) {
            // 현재 비밀번호와 입력한 비밀번호가 다를 경우 BAD_PASSWORD
            // 스프링 시큐리티 PasswordEncoder
            if (!passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword())) {
                throw new CustomException(BAD_PASSWORD);
            }
            // 일치하지만 새 비밀번호가 기존과 동일할 경우 SAME_PASSWORD
            if (requestDto.getChangedPassword().equals(requestDto.getCurrentPassword())) {
                throw new CustomException(SAME_PASSWORD);
            }
            // 일치하지만 새 비밀번호가 형식에 맞지 않을 경우 BAD_PASSWORD
            if (!requestDto.getChangedPassword().matches("^(?=.*?[A-Za-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{10,}$")) {
                throw new CustomException(BAD_PASSWORD);
            }
            requestDto.setChangedPassword(passwordEncoder.encode(requestDto.getChangedPassword()));
        } else { //비번을 바꾸지 않을 경우, 바꿀 비번을 현재 비번으로 설정
            requestDto.setChangedPassword(user.getPassword());
        }

        user.update(requestDto);
        user = getUserById(userId);
        return new ProfileResponseDto(user);
    }

    private User getUserById(Long userId) {
        return profileRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

}
