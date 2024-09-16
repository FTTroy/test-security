package com.github.fttroy.testsecurity.config;

import com.github.fttroy.testsecurity.jwt.JwtAuthFilter;
import com.github.fttroy.testsecurity.service.UserAuthDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserAuthDetailsService userDetailsService;

    @Autowired
    private JwtAuthFilter filter;

    /*
    FilterChain per gestire gli endpoint a cui gli utenti possono accedere in base ai ruoli
    Aggiunge il mio filtro custom in modo da essere eseguito prima del UsernamePasswordAuthenticationFilter
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.
                csrf(AbstractHttpConfigurer::disable).
                authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/home", "/register/**","/authenticate").permitAll();
                    registry.requestMatchers("/admin/**").hasRole("ADMIN");
                    registry.requestMatchers("/user/**").hasRole("USER");
                    registry.anyRequest().authenticated();
                })
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
/*
definisce degli usare precaricati in memoria
 */
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//                .username("user")
//                .password("$2a$12$PClwx.fejJqY3iSPBmSr3uK/8PJWaA6Fx3irZIJ308LNMwTZVBpZO")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("$2a$12$hrmJ9fIqU61JtELGWu1R5Oqp9nvteMOeds.6q6fHvQnPK0Icj29Ji")
//                .roles("admin")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    /*
    definisce il bean del mio userDetailsService Custom
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    /*
    definisce il bean di AuthenticationProvider settando il bean del passwordEncoder e quello del mio userDetailsService Custom
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(authenticationProvider());
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
