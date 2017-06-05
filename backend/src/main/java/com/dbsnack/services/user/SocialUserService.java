package com.dbsnack.services.user;

import com.dbsnack.domain.entities.users.SocialUser;
import org.springframework.social.facebook.api.User;

public interface SocialUserService extends BasicUserService {

    SocialUser register(User facebookUser);
}
