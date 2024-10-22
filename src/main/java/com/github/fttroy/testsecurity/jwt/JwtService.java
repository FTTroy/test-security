package com.github.fttroy.testsecurity.jwt;

import com.github.fttroy.testsecurity.otp.OtpService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {
    @Autowired
    private OtpService otpService;

    @Autowired
    JwtUtils jwtUtils;

    private static final long JWT_VALIDITY = TimeUnit.MINUTES.toMillis(30);

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .claim("claim1", "claim") //informazioni aggiuntive da poter inserire nel token
                .subject(userDetails.getUsername()) //specifica dell'user richiedente
                .issuedAt(Date.from(Instant.now())) //data di creazione del token
                .expiration(Date.from(Instant.now().plusMillis(JWT_VALIDITY))) //data di scadenza del token
                .signWith(jwtUtils.generateSecretKey()) //specifica la chiave segreta con il quale deve essere firmato
                .compact(); //converte in stringa
    }


    public String extractUsername(String jwt) {
        Claims claims = jwtUtils.getClaims(jwt);
        return claims.getSubject();
    }

    public boolean isTokenValid(String jwt) {
        Claims claims = jwtUtils.getClaims(jwt);
        return claims.getExpiration().after(Date.from(Instant.now()));
    }
}
