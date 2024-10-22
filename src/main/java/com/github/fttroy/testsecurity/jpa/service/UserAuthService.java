package com.github.fttroy.testsecurity.jpa.service;

import com.github.fttroy.testsecurity.jpa.entity.UserAuth;
import com.github.fttroy.testsecurity.jpa.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

    @Autowired
    private UserAuthRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserAuth save(UserAuth userAuth) {
        userAuth.setPassword(passwordEncoder.encode(userAuth.getPassword()));
        return repository.save(userAuth);
    }
}
