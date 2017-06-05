package com.dbsnack.repositories.training;

import com.dbsnack.domain.entities.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepository<T extends User> extends CrudRepository<T, Long> {

    T findOneByUsername(String username);
}
