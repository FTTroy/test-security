package com.github.fttroy.testsecurity.service;

import com.github.fttroy.testsecurity.records.Otp;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
@Data
public class OtpService {
    ConcurrentHashMap<String, Otp> map = new ConcurrentHashMap<>();

    private static final long VALIDITY = TimeUnit.MINUTES.toMillis(5);

    public Otp generateOtp(String username) {
        Otp otp = new Otp(generateRandom(), Date.from(Instant.now().plusMillis(VALIDITY)));
        map.put(username, otp);
        return otp;
    }

    public String generateRandom() {
        return String.valueOf(100000 + new SecureRandom().nextInt(900000));
    }
}
