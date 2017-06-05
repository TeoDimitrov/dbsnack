package com.dbsnack.controllers.auth;

import com.dbsnack.configuration.jwt.JWTConfigurer;
import com.dbsnack.configuration.jwt.TokenProvider;
import com.dbsnack.domain.entities.users.SocialUser;
import com.dbsnack.domain.models.viewModels.UserDetailsModel;
import com.dbsnack.exception.user.UserAlreadyRegisteredException;
import com.dbsnack.services.user.SocialUserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api/facebook")
public class FacebookLoginController {

    //10 minutes in order to consume
    private static final int COOKIE_AGE = 600_000;

    private final SocialUserService socialUserService;

    private final TokenProvider tokenProvider;

    private final Facebook facebook;

    private final ConnectionRepository connectionRepository;

    public FacebookLoginController(SocialUserService socialUserService,
                                   TokenProvider tokenProvider,
                                   Facebook facebook,
                                   ConnectionRepository connectionRepository) {
        this.socialUserService = socialUserService;
        this.tokenProvider = tokenProvider;
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @GetMapping("/authenticate")
    public String registerOrLogin(HttpServletResponse response) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }

        //Connect to Facebook
        ConnectionKey connectionKey = this.connectionRepository.findPrimaryConnection(Facebook.class).getKey();
        //[START]
        //Hack due to different API versions
        String userKey = this.connectionRepository.findPrimaryConnection(Facebook.class).getKey().getProviderUserId();
        String[] fields = {"email", "first_name", "last_name"};
        User facebookUser = this.facebook.fetchObject(userKey, User.class, fields);
        this.connectionRepository.removeConnection(connectionKey);
        //[END]

        //Register
        SocialUser socialUser;
        try {
            socialUser = this.socialUserService.register(facebookUser);
        } catch (UserAlreadyRegisteredException e) {
            return "redirect:/#/login?isEmailTaken=true";
        }

        //Authenticate
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(socialUser, null, socialUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        boolean rememberMe = true;
        UserDetailsModel userDetailsModel = this.socialUserService.getUserDetails(socialUser);
        String jwt = tokenProvider.createToken(authenticationToken, rememberMe, userDetailsModel);
        response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
        response.addCookie(this.getSocialAuthenticationCookie(jwt));
        return "redirect:/#/social/authenticate";

    }

    private Cookie getSocialAuthenticationCookie(String token) {
        Cookie socialAuthCookie = new Cookie("social-authentication", token);
        socialAuthCookie.setPath("/");
        socialAuthCookie.setMaxAge(COOKIE_AGE);
        return socialAuthCookie;
    }
}
