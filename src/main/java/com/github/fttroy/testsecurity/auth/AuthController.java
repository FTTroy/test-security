package com.github.fttroy.testsecurity.auth;

import com.github.fttroy.testsecurity.jwt.JwtService;
import com.github.fttroy.testsecurity.jwt.JwtUtils;
import com.github.fttroy.testsecurity.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MailService mailService;

    @Autowired
    private JwtUtils utils;

    @Autowired
    private UserAuthDetailsService authDetailsService;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/admin/home")
    public String adminHome() {
        return "home_admin";
    }

    @GetMapping("/user/home")
    public String userHome() {
        return "home_user";
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.username(), loginForm.password()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authDetailsService.loadUserByUsername(loginForm.username()));
        } else {
            throw new UsernameNotFoundException("Invalid Credentials");
        }
    }
}
