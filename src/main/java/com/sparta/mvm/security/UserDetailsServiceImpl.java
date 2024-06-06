package com.sparta.mvm.security;

import com.sparta.mvm.AuthTest.TestUser;
import com.sparta.mvm.entity.User;
import com.sparta.mvm.AuthTest.TestUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final TestUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TestUser user = userRepository.findByUsername(username);
        return new UserDetailsImpl(user);
    }
}
