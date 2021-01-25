package com.luv2code.illnesstracker.service;

import com.luv2code.illnesstracker.domain.Role;
import com.luv2code.illnesstracker.domain.User;
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
import java.util.Optional;

import static com.luv2code.illnesstracker.domain.enums.RoleType.*;
import static com.luv2code.illnesstracker.domain.enums.StatusType.ACTIVE;

@SpringBootTest
public class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    private Role userRole;
    private Role doctorRole;
    private Role adminRole;

    private List<Role> roles;

    @BeforeEach
    public void setup() {
        userRole = new Role();
        userRole.setId(1L);
        userRole.setName(USER);
        userRole.setDescription("The user role can perform CRUD operations over his/her profile and illnesses.");

        doctorRole = new Role();
        doctorRole.setId(2L);
        doctorRole.setName(DOCTOR);
        doctorRole.setDescription("The doctor role can perform specific CRUD operations over application users and illnesses.");

        adminRole = new Role();
        adminRole.setId(3L);
        adminRole.setName(ADMIN);
        adminRole.setDescription("The admin role can perform CRUD operations over all application users and illnesses.");

        final User firstUser = new User();
        firstUser.setFirstName("Michael");
        firstUser.setLastName("Jordan");
        firstUser.setEmail("michael.jordan23@gmail.com");
        firstUser.setPassword("theGoat23");
        firstUser.setDateOfBirth(LocalDate.of(1987, 10, 14));
        firstUser.setPhoneNumber("+385918742236");
        firstUser.setGender(GenderType.MALE);
        firstUser.setDateOfRegistration(LocalDateTime.now());
        firstUser.setStatus(ACTIVE);
        firstUser.setRoles(Collections.singletonList(userRole));

        userRole.setUsers(Collections.singletonList(firstUser));

        roles = new ArrayList<>();
        roles.add(userRole);
        roles.add(doctorRole);

        Mockito.when(roleRepository.findById(userRole.getId())).thenReturn(java.util.Optional.ofNullable(userRole));
        Mockito.when(roleRepository.findByName(doctorRole.getName())).thenReturn(java.util.Optional.ofNullable(doctorRole));
        Mockito.when(roleRepository.findAll()).thenReturn(roles);
    }

    @Test
    public void should_Return_When_Id_Is_Valid() {
        final Role searchedRole = roleService.findById(userRole.getId());

        Assertions.assertNotNull(searchedRole);
        Assertions.assertEquals(userRole, searchedRole);
        Assertions.assertEquals("1", String.valueOf(searchedRole.getId()));
        Assertions.assertEquals("USER", searchedRole.getName().name());
    }

    @Test
    public void should_Throw_Exception_When_Id_Is_Not_Valid() {
        Mockito.when(roleRepository.findById(doctorRole.getId()))
                .thenReturn(Optional.empty());

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
                .thenReturn(Optional.empty());

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
