package com.api.auth.service;

import com.api.auth.dto.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void send(Email email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("auth@gmail.com");
        simpleMailMessage.setText(email.body());
        simpleMailMessage.setSubject(email.subject());
        simpleMailMessage.setTo(email.to());
        javaMailSender.send(simpleMailMessage);
    }

}
