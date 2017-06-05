package com.dbsnack.services.account.implementation;

import com.dbsnack.domain.entities.users.StandardUser;
import com.dbsnack.repositories.user.StandardUserRepository;
import com.dbsnack.services.account.PasswordService;
import com.dbsnack.services.email.EmailService;
import com.dbsnack.utils.UserUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class PasswordServiceImpl implements PasswordService {

    private final StandardUserRepository standardUserRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final EmailService emailService;

    public PasswordServiceImpl(StandardUserRepository standardUserRepository,
                               BCryptPasswordEncoder bCryptPasswordEncoder,
                               EmailService emailService) {
        this.standardUserRepository = standardUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public void resetPassword(String username) {
        StandardUser standardUser = this.standardUserRepository.findOneByUsername(username);
        if (standardUser == null) {
            throw new UsernameNotFoundException(UserUtils.USER_DOES_NOT_EXIST_EXCEPTION_MESSAGE);
        }

        String newPassword = this.generateRandomPassword();
        String encryptedPassword = bCryptPasswordEncoder.encode(newPassword);
        standardUser.setPassword(encryptedPassword);

        this.emailService.sendResetPasswordEmail(username, newPassword);
    }

    private String generateRandomPassword() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
