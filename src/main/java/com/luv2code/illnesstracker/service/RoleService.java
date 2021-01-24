package com.luv2code.illnesstracker.service;

import com.luv2code.illnesstracker.domain.Role;
import com.luv2code.illnesstracker.domain.enums.RoleType;

import java.util.List;

public interface RoleService {

    Role findById(final Long id);

    Role findByName(final RoleType roleType);

    List<Role> findAll();

}
