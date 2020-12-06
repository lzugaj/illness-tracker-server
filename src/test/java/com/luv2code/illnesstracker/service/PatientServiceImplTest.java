package com.luv2code.illnesstracker.service;

import com.luv2code.illnesstracker.domain.GenderType;
import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.Role;
import com.luv2code.illnesstracker.exception.EmailAlreadyExistsException;
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

import static com.luv2code.illnesstracker.domain.RoleType.PATIENT;

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

        patients = new ArrayList<>();
        patients.add(secondPatient);

        patientRole.setPatients(patients);

        Mockito.when(patientRepository.save(firstPatient)).thenReturn(firstPatient);
        Mockito.when(roleService.findByName(PATIENT.name())).thenReturn(patientRole);
        Mockito.when(patientRepository.findById(secondPatient.getId())).thenReturn(java.util.Optional.ofNullable(secondPatient));
        Mockito.when(patientRepository.findAll()).thenReturn(patients);
    }

    @Test
    public void should_Save_Patients() {
        final Patient patient = patientService.save(firstPatient);

        Assertions.assertNotNull(patient);
        Assertions.assertEquals("1", String.valueOf(patient.getId()));
        Assertions.assertTrue(patient.getIsActive());
        Assertions.assertEquals(GenderType.MALE, patient.getGender());
    }

    @Test
    public void should_Thrown_Exception_When_Email_Already_Exists() {
        Mockito.when(patientRepository.save(secondPatient))
                .thenThrow(new EmailAlreadyExistsException(
                        "Patient",
                        "email",
                        secondPatient.getEmail()));

        final Exception exception = Assertions.assertThrows(
                EmailAlreadyExistsException.class,
                () -> patientService.save(secondPatient));

        final String expectedMessage = "Entity 'Patient' with 'email' value 'marie.curie@gmail.com' already exists.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void should_Return_Patient_When_Id_Is_Valid() {
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
    public void should_Return_List_Of_All_Patients() {
        final List<Patient> searchedPatients = patientService.findAll();

        Assertions.assertNotNull(searchedPatients);
        Assertions.assertEquals(patients.size(), searchedPatients.size());
        Assertions.assertEquals(1, searchedPatients.size());
    }

    // TODO: update

    @Test
    public void should_Delete_Patient() {
        patientService.delete(secondPatient);

        Mockito.verify(patientRepository, Mockito.times(1)).delete(secondPatient);
        Assertions.assertEquals(1, patients.size());
    }
}
