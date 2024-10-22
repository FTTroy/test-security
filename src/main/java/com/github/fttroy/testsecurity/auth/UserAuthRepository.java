package com.github.fttroy.testsecurity.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
    Optional<UserAuth> findByUsername(String username);

    Optional<UserAuth> findByEmail(String email);
}
