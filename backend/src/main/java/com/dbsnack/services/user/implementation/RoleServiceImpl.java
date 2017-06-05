package com.dbsnack.services.user.implementation;

import com.dbsnack.domain.entities.users.Role;
import com.dbsnack.repositories.user.RoleRepository;
import com.dbsnack.services.user.RoleService;
import com.dbsnack.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getDefaultUserRole() {
        return this.roleRepository.findOneByAuthority(UserUtils.DEFAULT_ROLE);
    }
}
