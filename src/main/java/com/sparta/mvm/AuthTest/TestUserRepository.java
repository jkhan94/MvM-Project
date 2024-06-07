package com.sparta.mvm.AuthTest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//public interface TestUserRepository extends JpaRepository<TestUser, Long> {
//    TestUser findByUsername(String username);
//}

public interface TestUserRepository extends JpaRepository<TestUser, Long> {
    Optional<TestUser> findByUsername(String username);
} 