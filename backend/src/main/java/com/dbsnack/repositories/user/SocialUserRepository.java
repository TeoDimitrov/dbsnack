package com.dbsnack.repositories.user;

import com.dbsnack.domain.entities.users.SocialUser;
import com.dbsnack.repositories.training.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialUserRepository extends UserRepository<SocialUser> {
}
