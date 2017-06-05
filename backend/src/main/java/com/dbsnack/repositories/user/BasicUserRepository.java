package com.dbsnack.repositories.user;

import com.dbsnack.domain.entities.users.User;
import com.dbsnack.repositories.training.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicUserRepository extends UserRepository<User> {
}

