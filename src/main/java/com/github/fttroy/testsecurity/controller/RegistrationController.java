package com.github.fttroy.testsecurity.controller;

import com.github.fttroy.testsecurity.entity.UserAuth;
import com.github.fttroy.testsecurity.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private UserAuthRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("register/user")
    public UserAuth createUser(@RequestBody UserAuth userAuth){
        userAuth.setPassword(passwordEncoder.encode(userAuth.getPassword()));
        return repository.save(userAuth);
    }
}
