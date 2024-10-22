package com.github.fttroy.testsecurity.otp;

import com.github.fttroy.testsecurity.auth.UserAuthRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {

    Logger log = LoggerFactory.getLogger(OtpService.class);
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    UserAuthRepository userRepository;

    public String generateOtp(String email) {
        log.info("generating - otp");
        Long id = userRepository.findByEmail(email).orElseThrow().getId();
        String otp = String.valueOf(100000 + new SecureRandom().nextInt(900000));
        redisTemplate.opsForValue().set(String.format("otp_%s", id), otp, 1, TimeUnit.MINUTES);
        log.info("generated - otp");
        return otp;
    }

    public boolean validateOtp(String otp, String id) {
        String cachedOtp = redisTemplate.opsForValue().get(String.format("otp_%s", id));
        return cachedOtp != null && cachedOtp.equals(otp);
    }
}
