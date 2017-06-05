package com.dbsnack.controllers.user;

import com.dbsnack.services.account.PasswordService;
import com.dbsnack.services.user.StandardUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
public class AccountController {

    private final StandardUserService standardUserService;

    private final PasswordService passwordService;

    public AccountController(StandardUserService standardUserService,
                             PasswordService passwordService) {
        this.standardUserService = standardUserService;
        this.passwordService = passwordService;
    }

    @GetMapping("/account/activate/{activationToken}")
    public String activateAccount(@PathVariable String activationToken) {
        this.standardUserService.activate(activationToken);

        return "redirect:/#/login?isActivated=true";
    }

    @PostMapping("/api/account/password/reset")
    @ResponseBody
    public void resetPassword(@RequestBody String username) {
        this.passwordService.resetPassword(username);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseBody
    public ResponseEntity handleTrainingDoesNotExists(UsernameNotFoundException e) {
        return new ResponseEntity<>(Collections.singletonMap("TrainingException",
                e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
    }
}
