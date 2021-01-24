package com.luv2code.illnesstracker.service.formatter;

import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.Role;
import com.luv2code.illnesstracker.domain.illness.Illness;
import com.luv2code.illnesstracker.repository.illness.IllnessRepository;
import com.luv2code.illnesstracker.service.PatientService;
import com.luv2code.illnesstracker.service.impl.illness.IllnessServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.luv2code.illnesstracker.domain.enums.GenderType.MALE;
import static com.luv2code.illnesstracker.domain.enums.RoleType.PATIENT;

@SpringBootTest
public class IllnessServiceImplTest {

    @InjectMocks
    private IllnessServiceImpl illnessService;

    @Mock
    private IllnessRepository illnessRepository;

    @Mock
    private PatientService patientService;

    private Illness firstIllness;
    private Illness secondIllness;
    private Illness thirdIllness;

    private Patient patient;

    private List<Illness> illnesses;

    @BeforeEach
    public void setup() {
        Role patientRole = new Role();
        patientRole.setId(1L);
        patientRole.setName(PATIENT);
        patientRole.setDescription("The user role can perform CRUD operations over his/her profile and illnesses.");

        patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("Michael");
        patient.setLastName("Jordan");
        patient.setEmail("michael.jordan23@gmail.com");
        patient.setUsername("michael");
        patient.setPassword("TheGoat23");
        patient.setDateOfBirth(LocalDate.of(1987, 4, 15));
        patient.setPhoneNumber("+385912346789");
        patient.setGender(MALE);
        patient.setBodyMassIndexes(null);
        patient.setHypertension(null);
        patient.setHyperthyroid(null);
        patient.setDiabetesMellitusTypesII(null);
        patient.setPainfulSyndromes(null);
        patient.setGastroEsophagealRefluxes(null);
        patient.setIsBodyMassIndexActive(false);
        patient.setIsHypertensionActive(false);
        patient.setIsHyperthyroidismActive(false);

        firstIllness = new Illness();
        firstIllness.setId(1L);
        firstIllness.setName("Body Mass Index");
        firstIllness.setIsSelected(false);

        secondIllness = new Illness();
        secondIllness.setId(2L);
        secondIllness.setName("Hypertension");
        secondIllness.setIsSelected(true);

        thirdIllness = new Illness();
        thirdIllness.setId(3L);
        thirdIllness.setName("Hyperthyroidism");
        thirdIllness.setIsSelected(false);

        illnesses = new ArrayList<>();
        illnesses.add(firstIllness);
        illnesses.add(secondIllness);
        illnesses.add(thirdIllness);

        Mockito.when(illnessRepository.findAll()).thenReturn(illnesses);
        Mockito.doNothing().when(patientService.update(patient, patient));
    }

    @Test
    public void should_Select_Illnesses() {
        final List<Illness> chosenIllnesses = illnessService.select(patient, illnesses);

        Assertions.assertNotNull(chosenIllnesses);
        Assertions.assertEquals("3", String.valueOf(illnesses.size()));
        Assertions.assertEquals("3", String.valueOf(chosenIllnesses.size()));
    }

    @Test
    public void should_Return_All_Illnesses() {
        final List<Illness> searchedIllnesses = illnessService.findAll();

        Assertions.assertNotNull(searchedIllnesses);
        Assertions.assertEquals("3", String.valueOf(illnesses.size()));
        Assertions.assertEquals("3", String.valueOf(searchedIllnesses.size()));
    }
}
