package com.luv2code.illnesstracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.enums.GenderType;
import com.luv2code.illnesstracker.dto.UserDto;
import com.luv2code.illnesstracker.dto.mapper.UserMapper;
import com.luv2code.illnesstracker.service.UserService;
import org.hamcrest.CoreMatchers;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(PatientController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    private User firstUser;
    private User secondUser;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();

        firstUser = new User();
        firstUser.setId(1L);
        firstUser.setFirstName("Michael");
        firstUser.setLastName("Jordan");
        firstUser.setEmail("michael.jordan23@gmail.com");
        firstUser.setUsername("michael");
        firstUser.setPassword("TheGoat23");
        firstUser.setDateOfBirth(LocalDate.parse("1987-12-12"));
        firstUser.setPhoneNumber("+385912346789");
        firstUser.setGender(GenderType.MALE);

        secondUser = new User();
        secondUser.setId(2L);
        secondUser.setFirstName("Marie");
        secondUser.setLastName("Curie");
        secondUser.setEmail("marie.curie@gmail.com");
        secondUser.setUsername("marie.curie");
        secondUser.setPassword("NuclearPower");
        secondUser.setDateOfBirth(LocalDate.parse("1987-12-12"));
        secondUser.setPhoneNumber("+385985661342");
        secondUser.setGender(GenderType.FEMALE);

        UserDto firstUserDto = new UserDto();
        firstUserDto.setId(1L);
        firstUserDto.setFirstName("Michael");
        firstUserDto.setLastName("Jordan");
        firstUserDto.setEmail("michael.jordan23@gmail.com");
        firstUserDto.setUsername("michael");
        firstUserDto.setDateOfBirth(LocalDate.parse("1987-12-12"));
        firstUserDto.setPhoneNumber("+385912346789");
        firstUserDto.setGender(GenderType.MALE);

        UserDto secondUserDto = new UserDto();
        secondUserDto.setId(2L);
        secondUserDto.setFirstName("Marie");
        secondUserDto.setLastName("Curie");
        secondUserDto.setEmail("marie.curie@gmail.com");
        secondUserDto.setUsername("marie.curie");
        secondUserDto.setDateOfBirth(LocalDate.parse("1987-12-12"));
        secondUserDto.setPhoneNumber("+385985661342");
        secondUserDto.setGender(GenderType.FEMALE);

        List<User> users = new ArrayList<>();
        users.add(firstUser);
        users.add(secondUser);

        List<UserDto> patientsDto = new ArrayList<>();
        patientsDto.add(firstUserDto);
        patientsDto.add(secondUserDto);

        BDDMockito.given(userService.findById(firstUser.getId())).willReturn(firstUser);
        BDDMockito.given(userService.findAll()).willReturn(users);
        BDDMockito.given(userService.update(firstUser, secondUser)).willReturn(secondUser);

        BDDMockito.given(userMapper.toUserDto(firstUser)).willReturn(firstUserDto);
        BDDMockito.given(userMapper.toUsersDto(users)).willReturn(patientsDto);
        BDDMockito.given(userMapper.toUserDto(secondUser)).willReturn(secondUserDto);
    }

    @Test
    public void when_Id_Is_Valid_Then_Returns_Patient() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/patients/{id}", firstUser.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("id", CoreMatchers.is(1)));
    }

    @Test
    public void should_Returns_List_Of_Patients() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/patients"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", Matchers.hasSize(2)));
    }

    @Test
    public void when_Id_Is_Valid_Then_Update_Patient() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/patients/{id}", firstUser.getId())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(secondUser)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id", CoreMatchers.is(2)));
    }

    @Test
    public void when_Id_Is_Valid_Then_Delete_Patient() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/patients/{id}", firstUser.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
