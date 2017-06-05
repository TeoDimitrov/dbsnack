package com.dbsnack.services.email.implementation;

import com.dbsnack.services.email.EmailService;
import com.dbsnack.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    @Override
    public void sendActivationEmail(String username, String activationToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(username);
        message.setSubject(EmailUtils.EMAIL_ACTIVATION_SUBJECT);
        message.setText(String.format(EmailUtils.EMAIL_ACTIVATION_BODY, activationToken));
        this.javaMailSender.send(message);
    }

    @Async
    @Override
    public void sendResetPasswordEmail(String username, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(username);
        message.setSubject(EmailUtils.EMAIL_PASSWORD_RESET_SUBJECT);
        message.setText(String.format(EmailUtils.EMAIL_RESET_PASSWORD_BODY, newPassword));
        this.javaMailSender.send(message);
    }
}
