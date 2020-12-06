package com.luv2code.illnesstracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.illnesstracker.domain.GenderType;
import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.dto.mapper.PatientMapper;
import com.luv2code.illnesstracker.service.PatientService;
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
        patient.setFirstName("Michael");
        patient.setLastName("Jordan");
        patient.setEmail("michael.jordan23@gmail.com");
        patient.setPassword("theGoat23");
        patient.setDateOfBirth(LocalDate.of(1975, 3, 12));
        patient.setOib("12821007094");
        patient.setPhoneNumber("+385987654321");
        patient.setGender(GenderType.MALE);

        BDDMockito.given(patientService.save(patient)).willReturn(patient);
    }
}
