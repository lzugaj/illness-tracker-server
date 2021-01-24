package com.luv2code.illnesstracker.service;

import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.Role;
import com.luv2code.illnesstracker.domain.enums.GenderType;
import com.luv2code.illnesstracker.exception.EntityNotFoundException;
import com.luv2code.illnesstracker.repository.RoleRepository;
import com.luv2code.illnesstracker.service.impl.RoleServiceImpl;
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

import static com.luv2code.illnesstracker.domain.enums.RoleType.*;
import static com.luv2code.illnesstracker.domain.enums.StatusType.ACTIVE;

@SpringBootTest
public class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    private Role patientRole;
    private Role doctorRole;
    private Role adminRole;

    private List<Role> roles;

    @BeforeEach
    public void setup() {
        patientRole = new Role();
        patientRole.setId(1L);
        patientRole.setName(PATIENT);
        patientRole.setDescription("The user role can perform CRUD operations over his/her profile and illnesses.");

        doctorRole = new Role();
        doctorRole.setId(2L);
        doctorRole.setName(DOCTOR);
        doctorRole.setDescription("The doctor role can perform specific CRUD operations over application users and illnesses.");

        adminRole = new Role();
        adminRole.setId(3L);
        adminRole.setName(ADMIN);
        adminRole.setDescription("The admin role can perform CRUD operations over all application users and illnesses.");

        final Patient firstPatient = new Patient();
        firstPatient.setFirstName("Michael");
        firstPatient.setLastName("Jordan");
        firstPatient.setEmail("michael.jordan23@gmail.com");
        firstPatient.setPassword("theGoat23");
        firstPatient.setDateOfBirth(LocalDate.of(1987, 10, 14));
        firstPatient.setPhoneNumber("+385918742236");
        firstPatient.setGender(GenderType.MALE);
        firstPatient.setDateOfRegistration(LocalDateTime.now());
        firstPatient.setStatus(ACTIVE);
        firstPatient.setRoles(Collections.singletonList(patientRole));

        patientRole.setPatients(Collections.singletonList(firstPatient));

        roles = new ArrayList<>();
        roles.add(patientRole);
        roles.add(doctorRole);

        Mockito.when(roleRepository.findById(patientRole.getId())).thenReturn(java.util.Optional.ofNullable(patientRole));
        Mockito.when(roleRepository.findByName(doctorRole.getName())).thenReturn(java.util.Optional.ofNullable(doctorRole));
        Mockito.when(roleRepository.findAll()).thenReturn(roles);
    }

    @Test
    public void should_Return_When_Id_Is_Valid() {
        final Role searchedRole = roleService.findById(patientRole.getId());

        Assertions.assertNotNull(searchedRole);
        Assertions.assertEquals(patientRole, searchedRole);
        Assertions.assertEquals("1", String.valueOf(searchedRole.getId()));
        Assertions.assertEquals("PATIENT", searchedRole.getName().name());
    }

    @Test
    public void should_Throw_Exception_When_Id_Is_Not_Valid() {
        Mockito.when(roleRepository.findById(doctorRole.getId()))
                .thenThrow(new EntityNotFoundException(
                        "Role",
                        "id",
                        String.valueOf(doctorRole.getId())));

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> roleService.findById(doctorRole.getId())
        );

        final String expectedMessage = "Entity 'Role' with 'id' value '2' was not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_When_Name_Is_Valid() {
        final Role searchedRole = roleService.findByName(doctorRole.getName());

        Assertions.assertNotNull(searchedRole);
        Assertions.assertEquals(doctorRole, searchedRole);
        Assertions.assertEquals("DOCTOR", searchedRole.getName().name());
    }

    @Test
    public void should_Throw_Exception_When_Name_Is_Not_Valid() {
        Mockito.when(roleRepository.findByName(adminRole.getName()))
                .thenThrow(new EntityNotFoundException(
                        "Role",
                        "name",
                        adminRole.getName().name()));

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> roleService.findByName(adminRole.getName()));

        final String expectedMessage = "Entity 'Role' with 'name' value 'ADMIN' was not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_List_Of_All_Roles() {
        final List<Role> searchedRoles = roleService.findAll();

        Assertions.assertNotNull(searchedRoles);
        Assertions.assertEquals(roles.size(), searchedRoles.size());
        Assertions.assertEquals(2, searchedRoles.size());
    }
}
