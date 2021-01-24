package com.luv2code.illnesstracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.enums.GenderType;
import com.luv2code.illnesstracker.dto.PatientDto;
import com.luv2code.illnesstracker.dto.mapper.PatientMapper;
import com.luv2code.illnesstracker.service.PatientService;
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
    private PatientService patientService;

    @MockBean
    private PatientMapper patientMapper;

    private ObjectMapper objectMapper;

    private Patient patient;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();

        patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("Michael");
        patient.setLastName("Jordan");
        patient.setEmail("michael.jordan23@gmail.com");
        patient.setUsername("michael");
        patient.setPassword("theGoat23");
        patient.setDateOfBirth(LocalDate.parse("1996-09-12"));
        patient.setPhoneNumber("+385987654321");
        patient.setGender(GenderType.MALE);

        PatientDto patientDto = new PatientDto();
        patientDto.setId(1L);
        patientDto.setFirstName("Michael");
        patientDto.setLastName("Jordan");
        patientDto.setEmail("michael.jordan23@gmail.com");
        patientDto.setUsername("michael");
        patientDto.setDateOfBirth(LocalDate.parse("1996-09-12"));
        patientDto.setPhoneNumber("+385987654321");
        patientDto.setGender(GenderType.MALE);

        BDDMockito.given(patientService.save(patient)).willReturn(patient);
        BDDMockito.given(patientMapper.toPatientDto(patient)).willReturn(patientDto);
    }

    @Test
    public void when_Valid_Input_Then_Register_Patient() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/registration")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(1)));
    }
}
