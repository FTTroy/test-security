package com.github.fttroy.testsecurity.mail;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class Mail {
    private String sender;
    private String receiver;
    private String subject;
    private String text;
}
