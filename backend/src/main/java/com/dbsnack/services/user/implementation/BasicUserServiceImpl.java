package com.dbsnack.services.user.implementation;

import com.dbsnack.domain.entities.users.SocialUser;
import com.dbsnack.domain.entities.users.User;
import com.dbsnack.domain.models.viewModels.UserDetailsModel;
import com.dbsnack.exception.user.UserDoesNotExistException;
import com.dbsnack.repositories.training.UserRepository;
import com.dbsnack.services.user.BasicUserService;
import com.dbsnack.utils.UserUtils;
import org.modelmapper.ModelMapper;

public abstract class BasicUserServiceImpl implements BasicUserService {

    private UserRepository basicUserRepository;

    private ModelMapper modelMapper;

    public BasicUserServiceImpl(UserRepository basicUserRepository, ModelMapper modelMapper) {
        this.basicUserRepository = basicUserRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean isUserTaken(String username) {
        User user = this.basicUserRepository.findOneByUsername(username);
        boolean isUserTaken = false;
        if (user != null) {
            isUserTaken = true;
        }

        return isUserTaken;
    }

    @Override
    public UserDetailsModel getUserDetails(String username) {
        User user = this.basicUserRepository.findOneByUsername(username);
        if (user == null) {
            throw new UserDoesNotExistException(UserUtils.USER_DOES_NOT_EXIST_EXCEPTION_MESSAGE);
        }

        return this.modelMapper.map(user, UserDetailsModel.class);
    }

    @Override
    public UserDetailsModel getUserDetails(SocialUser socialUser) {
        UserDetailsModel userDetailsModel = this.modelMapper.map(socialUser, UserDetailsModel.class);
        return userDetailsModel;
    }

    @Override
    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    @Override
    public User getUserByUsername(String username) {
        return this.basicUserRepository.findOneByUsername(username);
    }
}
