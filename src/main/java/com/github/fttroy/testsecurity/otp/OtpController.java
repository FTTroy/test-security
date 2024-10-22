package com.github.fttroy.testsecurity.otp;

import com.github.fttroy.testsecurity.jwt.JwtUtils;
import com.github.fttroy.testsecurity.mail.MailService;
import com.github.fttroy.testsecurity.otp.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otp")
public class OtpController {
    @Autowired
    private OtpService otpService;

    @Autowired
    private MailService mailService;

    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping("/generate")
    public ResponseEntity<Void> generateOtp(@RequestParam String receiver){
        String otp = otpService.generateOtp(receiver);
        mailService.sendMailWithOtp(otp, receiver);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate")
    public boolean validate(@RequestParam String otp, @RequestParam String email) {
        return otpService.validateOtp(otp, email);
    }
}
