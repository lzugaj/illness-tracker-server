package com.luv2code.illnesstracker.service;

import com.luv2code.illnesstracker.domain.GenderType;
import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.Role;
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

@SpringBootTest
public class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    private Role firstRole;
    private Role secondRole;

    private List<Role> roles;

    @BeforeEach
    public void setup() {
        firstRole = new Role();
        firstRole.setId(1L);
        firstRole.setName("PATIENT");

        secondRole = new Role();
        secondRole.setId(2L);
        secondRole.setName("DOCTOR");

        final Patient firstPatient = new Patient();
        firstPatient.setFirstName("Michael");
        firstPatient.setLastName("Jordan");
        firstPatient.setEmail("michael.jordan23@gmail.com");
        firstPatient.setPassword("theGoat23");
        firstPatient.setOib("64615810862");
        firstPatient.setDateOfBirth(LocalDate.of(1987, 10, 14));
        firstPatient.setPhoneNumber("+385918742236");
        firstPatient.setGender(GenderType.MALE);
        firstPatient.setDateOfRegistration(LocalDateTime.now());
        firstPatient.setIsActive(true);
        firstPatient.setRoles(Collections.singletonList(firstRole));

        firstRole.setPatients(Collections.singletonList(firstPatient));

        roles = new ArrayList<>();
        roles.add(firstRole);
        roles.add(secondRole);

        Mockito.when(roleRepository.findById(firstRole.getId())).thenReturn(java.util.Optional.ofNullable(firstRole));
        Mockito.when(roleRepository.findByName(secondRole.getName())).thenReturn(java.util.Optional.ofNullable(secondRole));
        Mockito.when(roleRepository.findAll()).thenReturn(roles);
    }

    @Test
    public void should_Return_Role_When_Id_Is_Valid() {
        final Role searchedRole = roleService.findById(firstRole.getId());

        Assertions.assertNotNull(searchedRole);
        Assertions.assertEquals(firstRole, searchedRole);
        Assertions.assertEquals("1", String.valueOf(searchedRole.getId()));
    }

    @Test
    public void should_Thrown_Exception_When_Id_Is_Not_Valid() {
        Mockito.when(roleRepository.findById(secondRole.getId()))
                .thenThrow(new EntityNotFoundException(
                        "Role",
                        "id",
                        String.valueOf(secondRole.getId())));

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> roleService.findById(secondRole.getId())
        );

        final String expectedMessage = "Entity 'Role' with 'id' value '2' was not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_Role_When_Name_Is_Valid() {
        final Role searchedRole = roleService.findByName(secondRole.getName());

        Assertions.assertNotNull(searchedRole);
        Assertions.assertEquals(secondRole, searchedRole);
        Assertions.assertEquals("DOCTOR", searchedRole.getName());
    }

    @Test
    public void should_Thrown_Exception_When_Name_Is_Not_Valid() {
        Mockito.when(roleRepository.findByName(firstRole.getName()))
                .thenThrow(new EntityNotFoundException(
                        "Role",
                        "name",
                        firstRole.getName()));

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> roleService.findByName(firstRole.getName()));

        final String expectedMessage = "Entity 'Role' with 'name' value 'PATIENT' was not founded.";
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
