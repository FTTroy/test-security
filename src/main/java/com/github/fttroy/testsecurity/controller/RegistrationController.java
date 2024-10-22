package com.github.fttroy.testsecurity.controller;

import com.github.fttroy.testsecurity.jpa.entity.UserAuth;
import com.github.fttroy.testsecurity.jpa.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private UserAuthService service;

    @PostMapping("register/user")
    public UserAuth createUser(@RequestBody UserAuth userAuth){
        return service.save(userAuth);
    }
}
