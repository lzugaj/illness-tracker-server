package com.luv2code.illnesstracker.controller;

import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.dto.UserDto;
import com.luv2code.illnesstracker.dto.mapper.UserMapper;
import com.luv2code.illnesstracker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
public class
RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    private final UserService userService;

    private final UserMapper userMapper;

    @Autowired
    public RegistrationController(final UserService userService,
                                  final UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody final User user) {
        final User newUser = userService.save(user);
        LOGGER.info("Successfully created new ´Patient´ with id: ´{}´.", newUser.getId());

        final UserDto dto = userMapper.toUserDto(newUser);
        LOGGER.info("Successfully mapped ´Patient´ to DTO.");
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
