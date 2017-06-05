package com.dbsnack.controllers.user;

import com.dbsnack.domain.models.bandingModels.RegisterModel;
import com.dbsnack.exception.user.UserAlreadyRegisteredException;
import com.dbsnack.services.user.StandardUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("api")
public class RegisterController {

    private final StandardUserService standardUserService;

    @Autowired
    public RegisterController(StandardUserService standardUserService) {
        this.standardUserService = standardUserService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/register")
    public void registerUser(@Valid @RequestBody RegisterModel registerModel) {
        this.standardUserService.register(registerModel);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity handleUserAlreadyRegistered(UserAlreadyRegisteredException e) {
        return new ResponseEntity<>(Collections.singletonMap("UserException",
                e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
    }
}
