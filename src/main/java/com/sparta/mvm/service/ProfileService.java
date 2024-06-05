package com.sparta.mvm.service;

import com.sparta.mvm.dto.ProfileResponseDto;
import com.sparta.mvm.entity.User;
import com.sparta.mvm.exception.CustomException;
import com.sparta.mvm.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import static com.sparta.mvm.exception.ErrorEnum.USER_NOT_FOUND;

@Service
public class ProfileService {
    ProfileRepository profileRepository;

    public ProfileResponseDto getProfile(Long userId) {
        User user = profileRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        return  new ProfileResponseDto(user);
    }
}
