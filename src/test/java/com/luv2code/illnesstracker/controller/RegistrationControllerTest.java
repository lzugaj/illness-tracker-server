package com.luv2code.illnesstracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.enums.GenderType;
import com.luv2code.illnesstracker.dto.UserDto;
import com.luv2code.illnesstracker.dto.mapper.UserMapper;
import com.luv2code.illnesstracker.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();

        user = new User();
        user.setId(1L);
        user.setFirstName("Michael");
        user.setLastName("Jordan");
        user.setEmail("michael.jordan23@gmail.com");
        user.setUsername("michael");
        user.setPassword("theGoat23");
        user.setDateOfBirth(LocalDate.parse("1996-09-12"));
        user.setPhoneNumber("+385987654321");
        user.setGender(GenderType.MALE);

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("Michael");
        userDto.setLastName("Jordan");
        userDto.setEmail("michael.jordan23@gmail.com");
        userDto.setUsername("michael");
        userDto.setDateOfBirth(LocalDate.parse("1996-09-12"));
        userDto.setPhoneNumber("+385987654321");
        userDto.setGender(GenderType.MALE);

        BDDMockito.given(userService.save(user)).willReturn(user);
        BDDMockito.given(userMapper.toUserDto(user)).willReturn(userDto);
    }

    @Test
    public void when_Valid_Input_Then_Register_Patient() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/registration")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(1)));
    }
}
