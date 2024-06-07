package com.sparta.mvm.repository;

import com.sparta.mvm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProfileRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId);
}
