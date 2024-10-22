package com.github.fttroy.testsecurity.auth;

import com.github.fttroy.testsecurity.auth.UserAuth;
import com.github.fttroy.testsecurity.auth.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthDetailsService implements UserDetailsService {

    @Autowired
    UserAuthRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuth user = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(getRoles(user.getRole()))
                .build();
    }

    private String[] getRoles(String roles){
        return roles != null ? roles.split(",") : new String[]{"USER"};
    }
}
