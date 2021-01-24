package com.luv2code.illnesstracker.service.illness;

import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.Role;
import com.luv2code.illnesstracker.domain.enums.GenderType;
import com.luv2code.illnesstracker.domain.illness.type.BodyMassIndex;
import com.luv2code.illnesstracker.domain.info.BodyMassIndexInfo;
import com.luv2code.illnesstracker.repository.illness.IllnessTypeRepository;
import com.luv2code.illnesstracker.service.PatientService;
import com.luv2code.illnesstracker.service.impl.illness.BodyMassIndexServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static com.luv2code.illnesstracker.domain.enums.RoleType.PATIENT;
import static com.luv2code.illnesstracker.domain.enums.StatusType.ACTIVE;

@SpringBootTest
public class AbstractBodyMassIndexServiceTest {

    @InjectMocks
    private BodyMassIndexServiceImpl bodyMassIndexService;

    @Mock
    private IllnessTypeRepository<BodyMassIndex> illnessTypeRepository;

    @Mock
    private PatientService patientService;

    private BodyMassIndex firstBodyMassIndex;

    @BeforeEach
    public void setup() {
        final Role patientRole = new Role();
        patientRole.setId(1L);
        patientRole.setName(PATIENT);
        patientRole.setDescription("The user role can perform CRUD operations over his/her profile and illnesses.");

        final Patient patient = new Patient();
        patient.setFirstName("Michael");
        patient.setLastName("Jordan");
        patient.setEmail("michael.jordan23@gmail.com");
        patient.setPassword("theGoat23");
        patient.setDateOfBirth(LocalDate.of(1987, 10, 14));
        patient.setPhoneNumber("+385918742236");
        patient.setGender(GenderType.MALE);
        patient.setDateOfRegistration(LocalDateTime.now());
        patient.setStatus(ACTIVE);
        patient.setRoles(Collections.singletonList(patientRole));

        patientRole.setPatients(Collections.singletonList(patient));

        final BodyMassIndexInfo firstBodyMassIndexInfo = new BodyMassIndexInfo();
        firstBodyMassIndexInfo.setId(1L);
        firstBodyMassIndexInfo.setValue("18.5-24.9");
        firstBodyMassIndexInfo.setClassification("Normal weight");

        firstBodyMassIndex = new BodyMassIndex();
        firstBodyMassIndex.setId(1L);
        firstBodyMassIndex.setHeight(190.0);
        firstBodyMassIndex.setWeight(82.5);
        firstBodyMassIndex.setIndexValue(22.9);
        firstBodyMassIndex.setDateOfPerformedMeasurement(LocalDateTime.now());
        firstBodyMassIndex.setBodyMassIndexInfo(firstBodyMassIndexInfo);
        firstBodyMassIndex.setPatients(Collections.singletonList(patient));

        firstBodyMassIndexInfo.setBodyMassIndexes(Collections.singletonList(firstBodyMassIndex));

        Mockito.when(illnessTypeRepository.findById(firstBodyMassIndex.getId())).thenReturn(java.util.Optional.of(firstBodyMassIndex));
    }

    @Test
    public void should_Find_BodyMassIndex_When_Id_Is_Founded() {
        final BodyMassIndex searchedBMI = bodyMassIndexService.findById(firstBodyMassIndex.getId());

        Assertions.assertEquals(firstBodyMassIndex, searchedBMI);
        Assertions.assertEquals("1", String.valueOf(searchedBMI.getId()));
    }
}
