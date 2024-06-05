package com.sparta.mvm.security;

import com.sparta.mvm.config.PasswordConfig;
import com.sparta.mvm.entity.TestUser;
//import com.sparta.mvm.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PasswordConfig passwordConfig;
    // TODO: userRepository 정의 후 user객체 받아올수 있도록 하기

    public UserDetailsServiceImpl(PasswordConfig passwordConfig) {
        this.passwordConfig = passwordConfig;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //User user =
        TestUser testUser = new TestUser();
        testUser.setPassword(passwordConfig.passwordEncoder().encode(testUser.getPassword()));
        return new UserDetailsImpl(testUser);
    }
}
