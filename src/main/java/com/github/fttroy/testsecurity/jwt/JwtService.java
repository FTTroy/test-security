package com.github.fttroy.testsecurity.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    private static final long VALIDITY = TimeUnit.MINUTES.toMillis(10);

    public String generateToken(UserDetails userDetails) {
        Map<String, String> claims = new HashMap<>();
        claims.put("iss", "me");
        return Jwts.builder()
                .claims(claims) //informazioni aggiuntive da poter inserire nel token
                .subject(userDetails.getUsername()) //specifica dell'user richiedente
                .issuedAt(Date.from(Instant.now())) //data di creazione del token
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY))) //data di scadenza del token
                .signWith(generateSecretKey()) //specifica la chiave segreta con il quale deve essere firmato
                .compact(); //converte in stringa
    }

    private SecretKey generateSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String extractUsername(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getSubject();
    }

    public Claims getClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(generateSecretKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    public boolean isTokenValid(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getExpiration().after(Date.from(Instant.now()));
    }

}