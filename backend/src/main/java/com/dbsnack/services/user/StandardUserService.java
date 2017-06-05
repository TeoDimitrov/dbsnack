package com.dbsnack.services.user;

import com.dbsnack.domain.models.bandingModels.RegisterModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface StandardUserService extends BasicUserService, UserDetailsService {

    void register(RegisterModel registerModel);

    void activate(String activationToken);
}
