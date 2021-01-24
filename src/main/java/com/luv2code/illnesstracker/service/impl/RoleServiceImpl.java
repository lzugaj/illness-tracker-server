package com.luv2code.illnesstracker.service.impl;

import com.luv2code.illnesstracker.domain.Role;
import com.luv2code.illnesstracker.domain.enums.RoleType;
import com.luv2code.illnesstracker.exception.EntityNotFoundException;
import com.luv2code.illnesstracker.repository.RoleRepository;
import com.luv2code.illnesstracker.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findById(final Long id) {
        final Optional<Role> searchedRole = roleRepository.findById(id);
        if (searchedRole.isPresent()) {
            LOGGER.info("Searching Role with id: ´{}´.", id);
            return searchedRole.get();
        } else {
            LOGGER.error("Not founded Role with id: ´{}´.", id);
            throw new EntityNotFoundException("Role", "id", String.valueOf(id));
        }
    }

    @Override
    public Role findByName(final RoleType roleType) {
        final Optional<Role> searchedRole = roleRepository.findByName(roleType);
        if (searchedRole.isPresent()) {
            LOGGER.info("Searching Role with name: ´{}´.", roleType.name());
            return searchedRole.get();
        } else {
            LOGGER.error("Not founded Role with name: ´{}´.", roleType.name());
            throw new EntityNotFoundException("Role", "name", roleType.name());
        }
    }

    @Override
    public List<Role> findAll() {
        final List<Role> roles = roleRepository.findAll();
        LOGGER.info("Searching all Roles.");
        return roles;
    }
}
