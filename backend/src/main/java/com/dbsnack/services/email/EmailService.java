package com.dbsnack.services.email;

public interface EmailService {

    void sendActivationEmail(String username, String activationToken);

    void sendResetPasswordEmail(String username, String newPassword);
}
