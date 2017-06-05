package com.dbsnack.repositories.user;

import com.dbsnack.domain.entities.users.StandardUser;
import com.dbsnack.repositories.training.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardUserRepository extends UserRepository<StandardUser> {

    StandardUser findOneByActivationToken(String activationToken);
}
