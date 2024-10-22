package com.github.fttroy.testsecurity.mail;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MailService {

    @Value("{$mail.sender}")
    private String sender;
    private final WebClient webClient;

    public MailService(@Qualifier("customWebClientBuilder") WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
    }

    public void sendMailWithOtp(String otp, String receiver) {
        Mail mail = Mail.builder()
                .sender(sender)
                .receiver(receiver)
                .subject("Verification Code")
                .text(String.format("This is your verification code: %s. It will expire in 5 minutes", otp))
                .build();
        webClient.post()
                .uri("/send-mail")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // tipo di contenuto per i model attribute
                .body(BodyInserters.fromFormData("sender", mail.getSender())
                        .with("receiver", mail.getReceiver())
                        .with("text", mail.getText())
                        .with("subject", mail.getSubject())
                )
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
