package com.luv2code.illnesstracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.illnesstracker.domain.illness.BodyMassIndex;
import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.Role;
import com.luv2code.illnesstracker.domain.enums.GenderType;
import com.luv2code.illnesstracker.service.BodyMassIndexService;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.luv2code.illnesstracker.domain.enums.RoleType.PATIENT;

@WebMvcTest(BodyMassIndexController.class)
public class BodyMassIndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BodyMassIndexService bodyMassIndexService;

    @MockBean
    private PatientService patientService;

    private Patient patient;

    private BodyMassIndex firstBMI;
    private BodyMassIndex secondBMI;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();

        Role patientRole = new Role();
        patientRole.setId(1L);
        patientRole.setName(PATIENT.name());

        patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("Michael");
        patient.setLastName("Jordan");
        patient.setEmail("michael.jordan23@gmail.com");
        patient.setPassword("TheGoat23");
        patient.setDateOfBirth(LocalDate.parse("1987-12-12"));
        patient.setPhoneNumber("+385912346789");
        patient.setOib("11238279839");
        patient.setGender(GenderType.MALE);
        patient.setIsActive(true);
        patient.setIsBodyMassIndexActive(false);
        patient.setIsHypertensionActive(true);
        patient.setIsHyperthyroidismActive(false);
        patient.setIsDiabetesMellitusTypeIIActive(true);
        patient.setIsPainfulSyndromesActive(true);
        patient.setIsGastroEsophagealRefluxActive(true);
        patient.setRoles(Collections.singletonList(patientRole));
        patient.setDateOfRegistration(LocalDateTime.now());

        patientRole.setPatients(Collections.singletonList(patient));

        firstBMI = new BodyMassIndex();
        firstBMI.setId(1L);
        firstBMI.setHeight(190.5);
        firstBMI.setWeight(103.2);
        firstBMI.setPatients(Collections.singletonList(patient));

        secondBMI = new BodyMassIndex();
        secondBMI.setId(2L);
        secondBMI.setHeight(164.0);
        secondBMI.setWeight(50.5);

        BodyMassIndex thirdBMI = new BodyMassIndex();
        thirdBMI.setId(3L);
        thirdBMI.setHeight(183.0);
        thirdBMI.setWeight(78.2);
        thirdBMI.setPatients(Collections.singletonList(patient));

        BodyMassIndex fourthBMI = new BodyMassIndex();
        fourthBMI.setId(4L);
        fourthBMI.setHeight(178.5);
        fourthBMI.setWeight(63.8);
        fourthBMI.setPatients(Collections.singletonList(patient));

        BodyMassIndex fifthBMI = new BodyMassIndex();
        fifthBMI.setId(5L);
        fifthBMI.setHeight(194.5);
        fifthBMI.setWeight(89.2);

        patient.setBodyMassIndexes(Collections.singletonList(firstBMI));
        patient.setBodyMassIndexes(Collections.singletonList(thirdBMI));
        patient.setBodyMassIndexes(Collections.singletonList(fourthBMI));

        List<BodyMassIndex> bodyMassIndexes = new ArrayList<>();
        bodyMassIndexes.add(firstBMI);
        bodyMassIndexes.add(secondBMI);
        bodyMassIndexes.add(thirdBMI);
        bodyMassIndexes.add(fourthBMI);
        bodyMassIndexes.add(fifthBMI);

        List<BodyMassIndex> patientBodyMassIndexes = new ArrayList<>();
        patientBodyMassIndexes.add(firstBMI);
        patientBodyMassIndexes.add(thirdBMI);
        patientBodyMassIndexes.add(fourthBMI);

        BDDMockito.given(patientService.findById(patient.getId())).willReturn(patient);
        BDDMockito.given(bodyMassIndexService.save(patient, firstBMI)).willReturn(firstBMI);
        BDDMockito.given(bodyMassIndexService.findById(secondBMI.getId())).willReturn(secondBMI);
        BDDMockito.given(bodyMassIndexService.findAll()).willReturn(bodyMassIndexes);
        BDDMockito.given(bodyMassIndexService.findAllForPatient(patient)).willReturn(patientBodyMassIndexes);
        BDDMockito.given(bodyMassIndexService.update(secondBMI, thirdBMI)).willReturn(secondBMI);
    }

    @Test
    public void when_Valid_Input_Then_Save_BMI() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/body-mass-indexes/patient/{id}", patient.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(firstBMI)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void when_Id_Is_Valid_Then_Returns_BMI() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/body-mass-indexes/{id}", secondBMI.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("id", CoreMatchers.is(2)));
    }

    @Test
    public void should_Returns_List_Of_BMI() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/body-mass-indexes"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", Matchers.hasSize(5)));
    }

    @Test
    public void should_Returns_List_Of_Patient_BMI() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/body-mass-indexes/patient/{id}", patient.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", Matchers.hasSize(3)));
    }

    @Test
    public void when_Id_Is_Valid_Then_Update_BMI() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/body-mass-indexes/{id}", secondBMI.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(firstBMI)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void when_Id_Is_Valid_Then_Delete_BMI() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/body-mass-indexes/{id}", secondBMI.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
