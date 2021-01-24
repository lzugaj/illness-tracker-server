package com.luv2code.illnesstracker.service;

import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.Role;
import com.luv2code.illnesstracker.exception.UsernameAlreadyExistsException;
import com.luv2code.illnesstracker.exception.EntityNotFoundException;
import com.luv2code.illnesstracker.repository.PatientRepository;
import com.luv2code.illnesstracker.service.impl.PatientServiceImpl;
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

import static com.luv2code.illnesstracker.domain.enums.GenderType.FEMALE;
import static com.luv2code.illnesstracker.domain.enums.GenderType.MALE;
import static com.luv2code.illnesstracker.domain.enums.RoleType.PATIENT;

@SpringBootTest
public class PatientServiceImplTest {

    @InjectMocks
    private PatientServiceImpl patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private RoleService roleService;

    private Patient firstPatient;
    private Patient secondPatient;

    private List<Patient> patients;

    @BeforeEach
    public void setup() {
        Role patientRole = new Role();
        patientRole.setId(1L);
        patientRole.setName(PATIENT);
        patientRole.setDescription("The user role can perform CRUD operations over his/her profile and illnesses.");

        firstPatient = new Patient();
        firstPatient.setId(1L);
        firstPatient.setFirstName("Michael");
        firstPatient.setLastName("Jordan");
        firstPatient.setEmail("michael.jordan23@gmail.com");
        firstPatient.setUsername("michael");
        firstPatient.setPassword("TheGoat23");
        firstPatient.setDateOfBirth(LocalDate.of(1987, 4, 15));
        firstPatient.setPhoneNumber("+385912346789");
        firstPatient.setGender(MALE);
        firstPatient.setBodyMassIndexes(null);
        firstPatient.setHypertension(null);
        firstPatient.setHyperthyroid(null);
        firstPatient.setDiabetesMellitusTypesII(null);

        secondPatient = new Patient();
        secondPatient.setId(2L);
        secondPatient.setFirstName("Marie");
        secondPatient.setLastName("Curie");
        secondPatient.setEmail("marie.curie@gmail.com");
        secondPatient.setUsername("marieCurie");
        secondPatient.setPassword("NuclearPower");
        secondPatient.setDateOfBirth(LocalDate.of(1968, 8, 23));
        secondPatient.setPhoneNumber("+385985661342");
        secondPatient.setGender(FEMALE);
        secondPatient.setBodyMassIndexes(null);
        secondPatient.setHypertension(null);
        secondPatient.setHyperthyroid(null);
        secondPatient.setDiabetesMellitusTypesII(null);

        patients = new ArrayList<>();
        patients.add(secondPatient);

        patientRole.setPatients(patients);

        Mockito.when(roleService.findByName(PATIENT)).thenReturn(patientRole);

        Mockito.when(patientRepository.save(firstPatient)).thenReturn(firstPatient);
        Mockito.when(patientRepository.findById(secondPatient.getId())).thenReturn(java.util.Optional.ofNullable(secondPatient));
        Mockito.when(patientRepository.findByUsername(secondPatient.getUsername())).thenReturn(java.util.Optional.ofNullable(secondPatient));
        Mockito.when(patientRepository.findAll()).thenReturn(patients);
    }

    @Test
    public void should_Save_Patient_When_Username_Does_Not_Exists() {
        final Patient patient = patientService.save(firstPatient);

        Assertions.assertNotNull(patient);
        Assertions.assertEquals(firstPatient, patient);
        Assertions.assertEquals("1", String.valueOf(patient.getId()));
        Assertions.assertEquals("ACTIVE", patient.getStatus().name());
    }

    @Test
    public void should_Throw_Exception_When_Username_Already_Exists_Save() {
        Mockito.when(patientRepository.save(secondPatient))
                .thenThrow(new UsernameAlreadyExistsException(
                        "Patient",
                        "username",
                        secondPatient.getUsername()));

        final Exception exception = Assertions.assertThrows(
                UsernameAlreadyExistsException.class,
                () -> patientService.save(secondPatient));

        final String expectedMessage = "Entity 'Patient' with 'username' value 'marieCurie' already exists.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void should_Find_Patient_When_Id_Is_Valid() {
        final Patient searchedPatient = patientService.findById(secondPatient.getId());

        Assertions.assertNotNull(searchedPatient);
        Assertions.assertEquals("2", String.valueOf(searchedPatient.getId()));
        Assertions.assertEquals(secondPatient, searchedPatient);
    }

    @Test
    public void should_Thrown_Exception_When_Id_Is_Not_Valid() {
        Mockito.when(patientRepository.findById(firstPatient.getId()))
                .thenThrow(new EntityNotFoundException(
                        "Patient",
                        "id",
                        String.valueOf(firstPatient.getId())));

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> patientService.findById(firstPatient.getId()));

        final String expectedMessage = "Entity 'Patient' with 'id' value '1' was not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void should_Find_Patient_When_Username_Not_Exists() {
        final Patient searchedPatient = patientService.findByUsername(secondPatient.getUsername());

        Assertions.assertNotNull(searchedPatient);
        Assertions.assertEquals("2", String.valueOf(searchedPatient.getId()));
        Assertions.assertEquals(secondPatient, searchedPatient);
    }

    @Test
    public void should_Thrown_Exception_When_Username_Already_Exists() {
        Mockito.when(patientRepository.findByUsername(firstPatient.getUsername()))
                .thenThrow(new EntityNotFoundException(
                        "Patient",
                        "username",
                        firstPatient.getUsername()));

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> patientService.findByUsername(firstPatient.getUsername()));

        final String expectedMessage = "Entity 'Patient' with 'username' value 'michael' was not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void should_Find_All_Patients() {
        final List<Patient> searchedPatients = patientService.findAll();

        Assertions.assertNotNull(searchedPatients);
        Assertions.assertEquals(patients.size(), searchedPatients.size());
        Assertions.assertEquals(1, searchedPatients.size());
    }

    @Test
    public void should_Update_Patient_When_Username_Does_Not_Exists() {
        final Patient updatedPatient = patientService.update(secondPatient, firstPatient);

        Assertions.assertNotNull(updatedPatient);
        Assertions.assertEquals("michael", updatedPatient.getUsername());
    }

    @Test
    public void should_Throw_Exception_When_Username_Already_Exists_Update() {
        Mockito.when(patientRepository.save(secondPatient))
                .thenThrow(new UsernameAlreadyExistsException(
                        "Patient",
                        "email",
                        secondPatient.getEmail()));

        final Exception exception = Assertions.assertThrows(
                UsernameAlreadyExistsException.class,
                () -> patientService.update(firstPatient, secondPatient));

        final String expectedMessage = "Entity 'Patient' with 'username' value 'marieCurie' already exists.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void should_Delete_Patient() {
        patientService.delete(secondPatient);

        Mockito.verify(patientRepository, Mockito.times(1)).delete(secondPatient);
        Assertions.assertEquals(1, patients.size());
    }
}
