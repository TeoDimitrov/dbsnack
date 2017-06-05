package com.dbsnack.controllers.auth;

import com.dbsnack.configuration.jwt.JWTConfigurer;
import com.dbsnack.configuration.jwt.JWTToken;
import com.dbsnack.configuration.jwt.TokenProvider;
import com.dbsnack.domain.models.bandingModels.LoginModel;
import com.dbsnack.domain.models.viewModels.UserDetailsModel;
import com.dbsnack.exception.user.UserDoesNotExistException;
import com.dbsnack.services.user.StandardUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/api")
public class LoginJWTController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    private final StandardUserService standardUserService;

    @Autowired
    public LoginJWTController(TokenProvider tokenProvider,
                              AuthenticationManager authenticationManager,
                              StandardUserService standardUserService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.standardUserService = standardUserService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity authorize(@Valid @RequestBody LoginModel loginModel, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginModel.getUsername(), loginModel.getPassword());

        try {
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            boolean rememberMe = (loginModel.getRememberMe() == null) ? false : loginModel.getRememberMe();
            UserDetailsModel userDetailsModel = this.standardUserService.getUserDetails(loginModel.getUsername());
            String jwt = tokenProvider.createToken(authentication, rememberMe, userDetailsModel);
            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
            return ResponseEntity.ok(new JWTToken(jwt));
        } catch (AuthenticationException | UserDoesNotExistException ae) {
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",
                    ae.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
}
