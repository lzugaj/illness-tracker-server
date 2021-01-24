package com.luv2code.illnesstracker.repository;

import com.luv2code.illnesstracker.domain.Role;
import com.luv2code.illnesstracker.domain.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(final RoleType roleType);

}
