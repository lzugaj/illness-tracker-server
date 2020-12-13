package com.luv2code.illnesstracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.enums.GenderType;
import com.luv2code.illnesstracker.dto.PatientDto;
import com.luv2code.illnesstracker.dto.mapper.PatientMapper;
import com.luv2code.illnesstracker.service.PatientService;
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
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private PatientMapper patientMapper;

    private Patient firstPatient;
    private Patient secondPatient;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();

        firstPatient = new Patient();
        firstPatient.setId(1L);
        firstPatient.setFirstName("Michael");
        firstPatient.setLastName("Jordan");
        firstPatient.setEmail("michael.jordan23@gmail.com");
        firstPatient.setPassword("TheGoat23");
        firstPatient.setDateOfBirth(LocalDate.parse("1987-12-12"));
        firstPatient.setPhoneNumber("+385912346789");
        firstPatient.setOib("11238279839");
        firstPatient.setGender(GenderType.MALE);

        secondPatient = new Patient();
        secondPatient.setId(2L);
        secondPatient.setFirstName("Marie");
        secondPatient.setLastName("Curie");
        secondPatient.setEmail("marie.curie@gmail.com");
        secondPatient.setPassword("NuclearPower");
        secondPatient.setDateOfBirth(LocalDate.parse("1987-12-12"));
        secondPatient.setPhoneNumber("+385985661342");
        secondPatient.setOib("68078116365");
        secondPatient.setGender(GenderType.FEMALE);

        PatientDto firstPatientDto = new PatientDto();
        firstPatientDto.setId(1L);
        firstPatientDto.setFirstName("Michael");
        firstPatientDto.setLastName("Jordan");
        firstPatientDto.setEmail("michael.jordan23@gmail.com");
        firstPatientDto.setDateOfBirth(LocalDate.parse("1987-12-12"));
        firstPatientDto.setPhoneNumber("+385912346789");
        firstPatientDto.setOib("11238279839");
        firstPatientDto.setGender(GenderType.MALE);

        PatientDto secondPatientDto = new PatientDto();
        secondPatientDto.setId(2L);
        secondPatientDto.setFirstName("Marie");
        secondPatientDto.setLastName("Curie");
        secondPatientDto.setEmail("marie.curie@gmail.com");
        secondPatientDto.setDateOfBirth(LocalDate.parse("1987-12-12"));
        secondPatientDto.setPhoneNumber("+385985661342");
        secondPatientDto.setOib("68078116365");
        secondPatientDto.setGender(GenderType.FEMALE);

        List<Patient> patients = new ArrayList<>();
        patients.add(firstPatient);
        patients.add(secondPatient);

        List<PatientDto> patientsDto = new ArrayList<>();
        patientsDto.add(firstPatientDto);
        patientsDto.add(secondPatientDto);

        BDDMockito.given(patientService.findById(firstPatient.getId())).willReturn(firstPatient);
        BDDMockito.given(patientMapper.toPatientDto(firstPatient)).willReturn(firstPatientDto);
        BDDMockito.given(patientService.findAll()).willReturn(patients);
        BDDMockito.given(patientMapper.toPatientsDto(patients)).willReturn(patientsDto);
        BDDMockito.given(patientService.update(firstPatient, secondPatient)).willReturn(secondPatient);
        BDDMockito.given(patientMapper.toPatientDto(secondPatient)).willReturn(secondPatientDto);
    }

    @Test
    public void when_Id_Is_Valid_Then_Returns_Patient() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/patients/{id}", firstPatient.getId()))
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
                .perform(MockMvcRequestBuilders.put("/patients/{id}", firstPatient.getId())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(secondPatient)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id", CoreMatchers.is(2)));
    }

    @Test
    public void when_Id_Is_Valid_Then_Delete_Patient() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/patients/{id}", firstPatient.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
