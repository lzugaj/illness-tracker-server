package com.luv2code.illnesstracker.service;

import com.luv2code.illnesstracker.domain.BodyMassIndex;
import com.luv2code.illnesstracker.domain.enums.GenderType;
import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.Role;
import com.luv2code.illnesstracker.domain.info.BodyMassIndexInfo;
import com.luv2code.illnesstracker.exception.EntityNotFoundException;
import com.luv2code.illnesstracker.repository.BodyMassIndexRepository;
import com.luv2code.illnesstracker.service.impl.BodyMassIndexServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.luv2code.illnesstracker.domain.enums.RoleType.PATIENT;

@SpringBootTest
public class BodyMassIndexServiceImplTest {

    @InjectMocks
    private BodyMassIndexServiceImpl bodyMassIndexService;

    @Mock
    private BodyMassIndexRepository bodyMassIndexRepository;

    @Mock
    private BodyMassIndexInfoService bodyMassIndexInfoService;

    private BodyMassIndex firstBMI;
    private BodyMassIndex secondBMI;
    private BodyMassIndex thirdBMI;
    private BodyMassIndex fourthBMI;
    private BodyMassIndex fifthBMI;

    private Patient firstPatient;
    private Patient secondPatient;

    private List<BodyMassIndex> bodyMassIndexes;

    @BeforeEach
    public void setup() {
        Role patientRole = new Role();
        patientRole.setId(1L);
        patientRole.setName(PATIENT.name());

        firstPatient = new Patient();
        firstPatient.setId(1L);
        firstPatient.setFirstName("Michael");
        firstPatient.setLastName("Jordan");
        firstPatient.setEmail("michael.jordan23@gmail.com");
        firstPatient.setPassword("TheGoat23");
        firstPatient.setDateOfBirth(LocalDate.of(1987, 4, 15));
        firstPatient.setPhoneNumber("+385912346789");
        firstPatient.setOib("11238279839");
        firstPatient.setGender(GenderType.MALE);
        firstPatient.setIsActive(true);
        firstPatient.setDateOfRegistration(LocalDateTime.now());
        firstPatient.setRoles(Collections.singletonList(patientRole));

        secondPatient = new Patient();
        secondPatient.setId(2L);
        secondPatient.setFirstName("Marie");
        secondPatient.setLastName("Curie");
        secondPatient.setEmail("marie.curie@gmail.com");
        secondPatient.setPassword("NuclearPower");
        secondPatient.setDateOfBirth(LocalDate.of(1968, 8, 23));
        secondPatient.setPhoneNumber("+385985661342");
        secondPatient.setOib("68078116365");
        secondPatient.setGender(GenderType.FEMALE);
        secondPatient.setIsActive(true);
        secondPatient.setDateOfRegistration(LocalDateTime.now());
        secondPatient.setRoles(Collections.singletonList(patientRole));

        patientRole.setPatients(Collections.singletonList(firstPatient));
        patientRole.setPatients(Collections.singletonList(secondPatient));

        BodyMassIndexInfo bodyMassIndexInfo = new BodyMassIndexInfo();
        bodyMassIndexInfo.setId(1L);
        bodyMassIndexInfo.setValue("25.0-29.9");
        bodyMassIndexInfo.setClassification("Overweight");

        firstBMI = new BodyMassIndex();
        firstBMI.setId(1L);
        firstBMI.setHeight(190.5);
        firstBMI.setWeight(103.2);
        firstBMI.setPatients(Collections.singletonList(firstPatient));

        secondBMI = new BodyMassIndex();
        secondBMI.setId(2L);
        secondBMI.setHeight(164.0);
        secondBMI.setWeight(50.5);
        secondBMI.setPatients(Collections.singletonList(secondPatient));

        thirdBMI = new BodyMassIndex();
        thirdBMI.setId(3L);
        thirdBMI.setHeight(183.0);
        thirdBMI.setWeight(78.2);
        thirdBMI.setPatients(Collections.singletonList(firstPatient));

        fourthBMI = new BodyMassIndex();
        fourthBMI.setId(4L);
        fourthBMI.setHeight(178.5);
        fourthBMI.setWeight(63.8);
        fourthBMI.setPatients(Collections.singletonList(firstPatient));

        fifthBMI = new BodyMassIndex();
        fifthBMI.setId(5L);
        fifthBMI.setHeight(194.5);
        fifthBMI.setWeight(89.2);
        fifthBMI.setPatients(Collections.singletonList(secondPatient));

        firstPatient.setBodyMassIndexes(Collections.singletonList(firstBMI));
        firstPatient.setBodyMassIndexes(Collections.singletonList(thirdBMI));
        firstPatient.setBodyMassIndexes(Collections.singletonList(fourthBMI));

        firstPatient.setBodyMassIndexes(Collections.singletonList(secondBMI));
        firstPatient.setBodyMassIndexes(Collections.singletonList(fifthBMI));

        bodyMassIndexes = new ArrayList<>();
        bodyMassIndexes.add(firstBMI);
        bodyMassIndexes.add(secondBMI);
        bodyMassIndexes.add(thirdBMI);
        bodyMassIndexes.add(fourthBMI);
        bodyMassIndexes.add(fifthBMI);

        Mockito.when(bodyMassIndexRepository.save(firstBMI)).thenReturn(firstBMI);
        Mockito.when(bodyMassIndexInfoService.findByIndexValue(28.4)).thenReturn(bodyMassIndexInfo);
        Mockito.when(bodyMassIndexRepository.findById(secondBMI.getId())).thenReturn(java.util.Optional.ofNullable(secondBMI));
        Mockito.when(bodyMassIndexRepository.findAll()).thenReturn(bodyMassIndexes);
    }

    @Test
    public void should_Save_BMI() {
        final BodyMassIndex newBodyMassIndex = bodyMassIndexService.save(firstPatient, firstBMI);

        Assertions.assertNotNull(newBodyMassIndex);
        Assertions.assertEquals("1", String.valueOf(newBodyMassIndex.getId()));
        Assertions.assertEquals("28.4", String.valueOf(newBodyMassIndex.getIndexValue()));
    }

    @Test
    public void should_Return_BMI_When_Id_Is_Valid() {
        final BodyMassIndex searchedBMI = bodyMassIndexService.findById(secondBMI.getId());

        Assertions.assertNotNull(searchedBMI);
        Assertions.assertEquals("2", String.valueOf(searchedBMI.getId()));
        Assertions.assertEquals(secondBMI, searchedBMI);
    }

    @Test
    public void should_Thrown_Exception_When_Id_Is_Not_Valid() {
        Mockito.when(bodyMassIndexRepository.findById(thirdBMI.getId()))
                .thenThrow(new EntityNotFoundException(
                        "BodyMassIndex",
                        "id",
                        String.valueOf(thirdBMI.getId())));

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> bodyMassIndexService.findById(thirdBMI.getId()));

        final String expectedMessage = "Entity 'BodyMassIndex' with 'id' value '3' was not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void should_Return_List_Of_All_BMI() {
        final List<BodyMassIndex> searchedBMI = bodyMassIndexService.findAll();

        Assertions.assertNotNull(searchedBMI);
        Assertions.assertEquals(5, searchedBMI.size());
        Assertions.assertEquals(bodyMassIndexes.size(), searchedBMI.size());
    }

    @Test
    public void should_Return_List_Of_All_BMI_For_Given_Patient() {
        final List<BodyMassIndex> searchedBMI = bodyMassIndexService.findAllForPatient(secondPatient);

        Assertions.assertNotNull(searchedBMI);
        Assertions.assertEquals(2, searchedBMI.size());
    }

    @Test
    public void should_Update_BMI() {
        final BodyMassIndex updatedBMI = bodyMassIndexService.update(fourthBMI, firstBMI);

        Assertions.assertNotNull(updatedBMI);
        Assertions.assertEquals("190.5", String.valueOf(updatedBMI.getHeight()));
        Assertions.assertEquals("103.2", String.valueOf(updatedBMI.getWeight()));
        Assertions.assertEquals("28.4", String.valueOf(updatedBMI.getIndexValue()));
    }

    @Test
    public void should_Delete_BMI() {
        bodyMassIndexService.delete(fifthBMI);

        Mockito.verify(bodyMassIndexRepository, Mockito.times(1)).delete(fifthBMI);
    }
}
