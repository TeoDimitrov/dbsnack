package com.dbsnack.services.user.implementation;

import com.dbsnack.domain.entities.users.Role;
import com.dbsnack.domain.entities.users.StandardUser;
import com.dbsnack.domain.entities.users.User;
import com.dbsnack.domain.models.bandingModels.RegisterModel;
import com.dbsnack.exception.user.UserAlreadyRegisteredException;
import com.dbsnack.repositories.training.UserRepository;
import com.dbsnack.repositories.user.StandardUserRepository;
import com.dbsnack.services.email.EmailService;
import com.dbsnack.services.user.RoleService;
import com.dbsnack.services.user.StandardUserService;
import com.dbsnack.utils.UserUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;

@Service
public class StandardUserServiceImpl extends BasicUserServiceImpl implements StandardUserService {

    private final StandardUserRepository standardUserRepository;

    private final UserRepository basicUserRepository;

    private final RoleService roleService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final EmailService emailService;

    public StandardUserServiceImpl(UserRepository basicUserRepository,
                                   ModelMapper modelMapper,
                                   StandardUserRepository standardUserRepository,
                                   RoleService roleService,
                                   BCryptPasswordEncoder bCryptPasswordEncoder,
                                   EmailService emailService) {
        super(basicUserRepository, modelMapper);
        this.standardUserRepository = standardUserRepository;
        this.basicUserRepository = basicUserRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
    }

    @Override
    public void register(RegisterModel registerModel) {
        StandardUser user = super.getModelMapper().map(registerModel, StandardUser.class);
        String encryptedPassword = this.bCryptPasswordEncoder.encode(registerModel.getPassword());
        user.setPassword(encryptedPassword);
        Date currentDate = new Date();
        user.setCreatedOn(currentDate);
        user.setAccountNonExpired(UserUtils.DEFAULT_IS_ACCOUNT_NON_EXPIRED);
        user.setAccountNonLocked(UserUtils.DEFAULT_IS_ACCOUNT_NON_LOCKED);
        user.setCredentialsNonExpired(UserUtils.DEFAULT_IS_CREDENTIALS_NON_EXPIRED);
        user.setEnabled(UserUtils.DEFAULT_IS_ENABLED);
        user.setActivationToken(UUID.randomUUID().toString());
        Role defaultUserRole = this.roleService.getDefaultUserRole();
        user.addRole(defaultUserRole);
        boolean isUserAlreadyRegistered = super.isUserTaken(user.getUsername());
        if (isUserAlreadyRegistered) {
            throw new UserAlreadyRegisteredException(UserUtils.USER_ALREADY_REGISTERED_EXCEPTION_MESSAGE);
        }

        this.emailService.sendActivationEmail(user.getUsername(), user.getActivationToken());

        this.standardUserRepository.save(user);
    }

    @Override
    @Transactional
    public void activate(String activationToken) {
        StandardUser user = this.standardUserRepository.findOneByActivationToken(activationToken);
        user.setEnabled(true);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.standardUserRepository.findOneByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(UserUtils.INVALID_CREDENTIALS);
        }

        return user;
    }
}
