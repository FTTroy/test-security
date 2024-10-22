package com.github.fttroy.testsecurity.opt;

import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

public class OtpServiceTest {

    @Test
    public void generateOtp(){
        SecureRandom random = new SecureRandom();
        String otp = String.valueOf(100000+ random.nextInt(900000));
        System.out.println(otp);
    }
}
