package com.luv2code.illnesstracker.service;

import com.luv2code.illnesstracker.domain.Role;

import java.util.List;

public interface RoleService {

    Role findById(final Long id);

    Role findByName(final String name);

    List<Role> findAll();

}
