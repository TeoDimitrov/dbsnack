package com.dbsnack.services.user;

import com.dbsnack.domain.entities.users.SocialUser;
import com.dbsnack.domain.entities.users.User;
import com.dbsnack.domain.models.viewModels.UserDetailsModel;
import org.modelmapper.ModelMapper;

public interface BasicUserService {

    boolean isUserTaken(String username);

    UserDetailsModel getUserDetails(String username);

    UserDetailsModel getUserDetails(SocialUser socialUser);

    ModelMapper getModelMapper();

    User getUserByUsername(String username);
}
