package com.luv2code.illnesstracker.controller;

import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.dto.UserDto;
import com.luv2code.illnesstracker.dto.mapper.UserMapper;
import com.luv2code.illnesstracker.service.UserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/patients")
@Api("Patient Controller")
public class PatientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

    private final UserService userService;

    private final UserMapper userMapper;

    @Autowired
    public PatientController(final UserService userService,
                             final UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable final Long id) {
        final User searchedUser = userService.findById(id);
        LOGGER.info("Successfully founded ´Patient´ with id: ´{}´.", id);

        final UserDto dto = userMapper.toUserDto(searchedUser);
        LOGGER.info("Successfully mapped ´Patient´ to DTO.");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        final List<User> users = userService.findAll();
        LOGGER.info("Successfully founded all ´Patient´.");

        final List<UserDto> dto = userMapper.toUsersDto(users);
        LOGGER.info("Successfully mapped ´Patient´ to DTO.");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @Valid @RequestBody final User newUser) {
        final User searchedUser = userService.findById(id);
        LOGGER.info("Successfully founded ´Patient´ with id: ´{}´.", id);

        final User updatedUser = userService.update(searchedUser, newUser);
        LOGGER.info("Successfully updated ´Patient´ with id: ´{}´.", id);

        final UserDto dto = userMapper.toUserDto(updatedUser);
        LOGGER.info("Successfully mapped ´Patient´ to DTO.");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        final User searchedUser = userService.findById(id);
        LOGGER.info("Successfully founded ´Patient´ with id: ´{}´.", id);

        userService.delete(searchedUser);
        LOGGER.info("Successfully deleted ´Patient´ with id: ´{}´.", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
