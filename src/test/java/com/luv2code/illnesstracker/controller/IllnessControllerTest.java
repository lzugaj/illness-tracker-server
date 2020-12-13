package com.luv2code.illnesstracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.illnesstracker.domain.Illness;
import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.Role;
import com.luv2code.illnesstracker.domain.enums.GenderType;
import com.luv2code.illnesstracker.service.IllnessService;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.luv2code.illnesstracker.domain.enums.RoleType.PATIENT;

@WebMvcTest(IllnessController.class)
public class IllnessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IllnessService illnessService;

    @MockBean
    private PatientService patientService;

    private Patient patient;

    private List<Illness> illnesses;

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

        Illness bodyMassIndex = new Illness();
        bodyMassIndex.setId(1L);
        bodyMassIndex.setName("Body Mass Index");
        bodyMassIndex.setIsSelected(false);

        Illness hypertension = new Illness();
        hypertension.setId(2L);
        hypertension.setName("Hypertension");
        hypertension.setIsSelected(true);

        Illness hyperthyroidism = new Illness();
        hyperthyroidism.setId(3L);
        hyperthyroidism.setName("Hyperthyroidism");
        hyperthyroidism.setIsSelected(false);

        Illness diabetesMellitusTypeII = new Illness();
        diabetesMellitusTypeII.setId(4L);
        diabetesMellitusTypeII.setName("Diabetes Mellitus Type II");
        diabetesMellitusTypeII.setIsSelected(false);

        Illness painfulSyndromes = new Illness();
        painfulSyndromes.setId(5L);
        painfulSyndromes.setName("Painful Syndromes");
        painfulSyndromes.setIsSelected(true);

        Illness gastroEsophagealReflux = new Illness();
        gastroEsophagealReflux.setId(6L);
        gastroEsophagealReflux.setName("Gastro Esophageal Reflux");
        gastroEsophagealReflux.setIsSelected(false);

        illnesses = new ArrayList<>();
        illnesses.add(bodyMassIndex);
        illnesses.add(hypertension);
        illnesses.add(hyperthyroidism);
        illnesses.add(diabetesMellitusTypeII);
        illnesses.add(painfulSyndromes);
        illnesses.add(gastroEsophagealReflux);

        BDDMockito.given(patientService.findById(patient.getId())).willReturn(patient);
        BDDMockito.given(illnessService.select(patient, illnesses)).willReturn(illnesses);
        BDDMockito.given(illnessService.findAll()).willReturn(illnesses);
    }

    @Test
    public void when_Valid_Input_Then_Select_Patient_Illnesses() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/illnesses/patient/{id}", patient.getId())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(illnesses)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void should_Returns_List_Of_Illnesses() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/illnesses"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", Matchers.hasSize(6)));
    }
}
