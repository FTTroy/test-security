package com.github.fttroy.testsecurity.repository;

import com.github.fttroy.testsecurity.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuth,Long> {
    Optional<UserAuth> findByUsername(String username);
}
