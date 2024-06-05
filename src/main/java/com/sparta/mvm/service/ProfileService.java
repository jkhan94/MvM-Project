package com.sparta.mvm.service;

import com.sparta.mvm.dto.ProfileRequestDto;
import com.sparta.mvm.dto.ProfileResponseDto;
import com.sparta.mvm.entity.User;
import com.sparta.mvm.exception.CustomException;
import com.sparta.mvm.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.mvm.exception.ErrorEnum.*;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileResponseDto getProfile(Long userId) {
        User user = profileRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        ProfileResponseDto responseDto = new ProfileResponseDto(user);
        return responseDto;
    }

    @Transactional
    public ProfileResponseDto updateSchedule(Long userId, ProfileRequestDto requestDto) {
        User user = profileRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 비밀번호를 바꿀 때
        // 현재 비밀번호와 입력한 비번이 다를 경우 BAD_PASSWORD
        // 일치하지만 바꾸려는 비번이 기존과 동일할 경우 SAME_PASSWORD
        // 일치하지만 비밀번호 형식과 다를 경우 BAD_PASSWORD
        if (!requestDto.getChangedPassword().isEmpty()) {
            if (!requestDto.getCurrentPassword().equals(user.getPassword())) {
                    throw new CustomException(BAD_PASSWORD);
            }
            if(requestDto.getChangedPassword().equals(requestDto.getCurrentPassword())) {
                throw new CustomException(SAME_PASSWORD);
            }
            if(!requestDto.getChangedPassword().matches("^(?=.*?[A-Za-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{10,}$")){
                throw new CustomException(BAD_PASSWORD);
            }
        } else { //비번을 바꾸지 않을 경우, 바꿀 비번을 현재 비번으로 설정
            requestDto.setChangedPassword(requestDto.getCurrentPassword());
        }

        user.update(requestDto);
        user = profileRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        return new ProfileResponseDto(user);
    }

}
