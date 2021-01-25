package com.luv2code.illnesstracker.service.impl;

import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.Role;
import com.luv2code.illnesstracker.domain.enums.RoleType;
import com.luv2code.illnesstracker.exception.EntityNotFoundException;
import com.luv2code.illnesstracker.exception.UsernameAlreadyExistsException;
import com.luv2code.illnesstracker.repository.UserRepository;
import com.luv2code.illnesstracker.service.UserService;
import com.luv2code.illnesstracker.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.luv2code.illnesstracker.domain.enums.StatusType.ACTIVE;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public User save(final User user) {
        if (doesUsernameNotExists(user)) {
            setupVariablesCreate(user);
            LOGGER.info("Successfully setup variables for User with username: ´{}´.", user.getUsername());

            final User newUser = userRepository.save(user);
            LOGGER.info("Saving new User with id: ´{}´.", user.getId());
            return newUser;
        } else {
            LOGGER.error("User already exists with username: ´{}´.", user.getUsername());
            throw new UsernameAlreadyExistsException("User", "username", user.getUsername());
        }
    }

    private void setupVariablesCreate(final User user) {
        LOGGER.info("Setting up variables for User with username: ´{}´.", user.getUsername());
        user.setDateOfRegistration(LocalDateTime.now());
        user.setStatus(ACTIVE);
        user.setIsBodyMassIndexActive(false);
        user.setIsHypertensionActive(false);
        user.setIsHyperthyroidismActive(false);
        user.setIsDiabetesMellitusTypeIIActive(false);
        user.setIsPainfulSyndromesActive(false);
        user.setIsGastroEsophagealRefluxActive(false);

        final Role role = roleService.findByName(RoleType.USER);
        LOGGER.info("Successfully founded Role with name: ´{}´.", role.getName());

        user.setRoles(Collections.singletonList(role));

        LOGGER.info("Setting up Role variables for User with email: ´{}´.", user.getEmail());
        role.setUsers(Collections.singletonList(user));
    }

    @Override
    public User findById(Long id) {
        final Optional<User> searchedUser = userRepository.findById(id);
        if (searchedUser.isPresent()) {
            LOGGER.info("Searching User with id: ´{}´.", id);
            return searchedUser.get();
        } else {
            LOGGER.error("Not founded User with id: ´{}´.", id);
            throw new EntityNotFoundException("User", "id", String.valueOf(id));
        }
    }

    @Override
    public User findByUsername(final String username) {
        final Optional<User> searchedUser = userRepository.findByUsername(username);
        if (searchedUser.isPresent()) {
            LOGGER.info("Searching User with username: ´{}´.", username);
            return searchedUser.get();
        } else {
            LOGGER.error("Not founded User with username: ´{}´.", username);
            throw new EntityNotFoundException("User", "username", username);
        }
    }

    @Override
    public List<User> findAll() {
        final List<User> users = userRepository.findAll();
        LOGGER.info("Searching all Users.");
        return users;
    }

    @Override
    public User update(final User oldUser, final User newUser) {
        setupVariablesUpdate(oldUser, newUser);
        LOGGER.info("Successfully update variables for User with email: ´{}´.", oldUser.getEmail());

        userRepository.save(oldUser);
        LOGGER.info("Updating User with id: ´{}´.", oldUser.getId());
        return oldUser;
    }

    private void setupVariablesUpdate(final User oldUser, final User newUser) {
        LOGGER.info("Updating variables for User with email: ´{}´.", oldUser.getEmail());
        checkUsername(oldUser, newUser);
        oldUser.setFirstName(newUser.getFirstName());
        oldUser.setLastName(newUser.getLastName());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setDateOfBirth(newUser.getDateOfBirth());
        oldUser.setPhoneNumber(newUser.getPhoneNumber());
        oldUser.setGender(newUser.getGender());
    }

    private void checkUsername(final User oldUser, final User newUser) {
        LOGGER.info("Checking if username already exists for User with id: ´{}´.", oldUser.getId());
        if (newUser.getUsername().equals(oldUser.getUsername())) {
            oldUser.setUsername(newUser.getUsername());
        } else {
            if (doesUsernameNotExists(newUser)) {
                oldUser.setUsername(newUser.getUsername());
            } else {
                LOGGER.error("User already exists with email: ´{}´.", newUser.getUsername());
                throw new UsernameAlreadyExistsException("User", "username", newUser.getUsername());
            }
        }
    }

    private boolean doesUsernameNotExists(final User searchedUser) {
        final List<User> users = findAll();
        LOGGER.info("Searching User with username: ´{}´.", searchedUser.getUsername());
        return users.stream()
                .noneMatch(user -> user.getUsername().equals(searchedUser.getUsername()));
    }

    @Override
    public void delete(final User user) {
        LOGGER.info("Deleting User with id: ´{}´.", user.getId());
        userRepository.delete(user);
    }
}
