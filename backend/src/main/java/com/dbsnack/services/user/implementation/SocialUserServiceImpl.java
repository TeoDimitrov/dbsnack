package com.dbsnack.services.user.implementation;

import com.dbsnack.domain.entities.users.Role;
import com.dbsnack.domain.entities.users.SocialMedia;
import com.dbsnack.domain.entities.users.SocialUser;
import com.dbsnack.exception.user.UserAlreadyRegisteredException;
import com.dbsnack.repositories.user.BasicUserRepository;
import com.dbsnack.repositories.user.SocialUserRepository;
import com.dbsnack.services.user.RoleService;
import com.dbsnack.services.user.SocialUserService;
import com.dbsnack.utils.UserUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class SocialUserServiceImpl extends BasicUserServiceImpl implements SocialUserService {

    private final BasicUserRepository basicUserRepository;

    private final SocialUserRepository socialUserRepository;

    private final RoleService roleService;

    private final ModelMapper modelMapper;

    @Autowired
    public SocialUserServiceImpl(BasicUserRepository basicUserRepository,
                                 SocialUserRepository socialUserRepository,
                                 RoleService roleService,
                                 ModelMapper modelMapper) {
        super(basicUserRepository, modelMapper);
        this.basicUserRepository = basicUserRepository;
        this.socialUserRepository = socialUserRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @Override
    public SocialUser register(User facebookUser) {
        String email = facebookUser.getEmail();
        String firstName = facebookUser.getFirstName();
        String lastName = facebookUser.getLastName();
        SocialUser socialUser = this.socialUserRepository.findOneByUsername(email);
        if (socialUser == null) {
            socialUser = registerUser(email, firstName, lastName);
        }

        return socialUser;
    }

    @Transactional
    private SocialUser registerUser(String email, String firstName, String lastName) {
        if (super.isUserTaken(email)) {
            throw new UserAlreadyRegisteredException(UserUtils.USER_ALREADY_REGISTERED_EXCEPTION_MESSAGE);
        }

        SocialUser socialUser = new SocialUser();
        socialUser.setUsername(email);
        socialUser.setFirstName(firstName);
        socialUser.setLastName(lastName);
        socialUser.setSocialMedia(SocialMedia.FACEBOOK);
        Date currentDate = new Date();
        socialUser.setCreatedOn(currentDate);
        socialUser.setAccountNonExpired(UserUtils.DEFAULT_IS_ACCOUNT_NON_EXPIRED);
        socialUser.setAccountNonLocked(UserUtils.DEFAULT_IS_ACCOUNT_NON_LOCKED);
        socialUser.setCredentialsNonExpired(UserUtils.DEFAULT_IS_CREDENTIALS_NON_EXPIRED);
        socialUser.setEnabled(UserUtils.DEFAULT_IS_SOCIAL_ENABLED);
        Role defaultUserRole = this.roleService.getDefaultUserRole();
        socialUser.addRole(defaultUserRole);
        this.socialUserRepository.save(socialUser);
        return socialUser;
    }
}
