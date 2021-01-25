package com.luv2code.illnesstracker.service;

import com.luv2code.illnesstracker.domain.Role;
import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.exception.EntityNotFoundException;
import com.luv2code.illnesstracker.exception.UsernameAlreadyExistsException;
import com.luv2code.illnesstracker.repository.UserRepository;
import com.luv2code.illnesstracker.service.impl.UserServiceImpl;
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
import java.util.Optional;

import static com.luv2code.illnesstracker.domain.enums.GenderType.FEMALE;
import static com.luv2code.illnesstracker.domain.enums.GenderType.MALE;
import static com.luv2code.illnesstracker.domain.enums.RoleType.USER;

@SpringBootTest
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    private User firstUser;
    private User secondUser;
    private User thirdUser;

    private List<User> users;

    @BeforeEach
    public void setup() {
        Role userRole = new Role();
        userRole.setId(1L);
        userRole.setName(USER);
        userRole.setDescription("The user role can perform CRUD operations over his/her profile and illnesses.");

        firstUser = new User();
        firstUser.setId(1L);
        firstUser.setFirstName("Michael");
        firstUser.setLastName("Jordan");
        firstUser.setEmail("michael.jordan23@gmail.com");
        firstUser.setUsername("michael");
        firstUser.setPassword("TheGoat23");
        firstUser.setDateOfBirth(LocalDate.of(1987, 4, 15));
        firstUser.setPhoneNumber("+385912346789");
        firstUser.setGender(MALE);
        firstUser.setBodyMassIndexes(null);
        firstUser.setHypertension(null);
        firstUser.setHyperthyroid(null);
        firstUser.setDiabetesMellitusTypesII(null);

        secondUser = new User();
        secondUser.setId(2L);
        secondUser.setFirstName("Marie");
        secondUser.setLastName("Curie");
        secondUser.setEmail("marie.curie@gmail.com");
        secondUser.setUsername("marieCurie");
        secondUser.setPassword("NuclearPower");
        secondUser.setDateOfBirth(LocalDate.of(1968, 8, 23));
        secondUser.setPhoneNumber("+385985661342");
        secondUser.setGender(FEMALE);
        secondUser.setBodyMassIndexes(null);
        secondUser.setHypertension(null);
        secondUser.setHyperthyroid(null);
        secondUser.setDiabetesMellitusTypesII(null);

        thirdUser = new User();
        thirdUser.setId(3L);
        thirdUser.setFirstName("Alan");
        thirdUser.setLastName("Ford");
        thirdUser.setEmail("alan.ford@gmail.com");
        thirdUser.setUsername("aford");
        thirdUser.setPassword("Comics");
        thirdUser.setDateOfBirth(LocalDate.of(1963, 4, 12));
        thirdUser.setPhoneNumber("+385985662222");
        thirdUser.setGender(MALE);
        thirdUser.setBodyMassIndexes(null);
        thirdUser.setHypertension(null);
        thirdUser.setHyperthyroid(null);
        thirdUser.setDiabetesMellitusTypesII(null);

        users = new ArrayList<>();
        users.add(secondUser);
        users.add(thirdUser);

        userRole.setUsers(users);

        Mockito.when(roleService.findByName(USER)).thenReturn(userRole);

        Mockito.when(userRepository.save(firstUser)).thenReturn(firstUser);
        Mockito.when(userRepository.findById(secondUser.getId())).thenReturn(java.util.Optional.ofNullable(secondUser));
        Mockito.when(userRepository.findByUsername(secondUser.getUsername())).thenReturn(java.util.Optional.ofNullable(secondUser));
        Mockito.when(userRepository.findAll()).thenReturn(users);
    }

    @Test
    public void should_Save_User_When_Username_Does_Not_Exists() {
        final User user = userService.save(firstUser);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(firstUser, user);
        Assertions.assertEquals("1", String.valueOf(user.getId()));
        Assertions.assertEquals("ACTIVE", user.getStatus().name());
    }

    @Test
    public void should_Throw_Exception_When_Username_Already_Exists_Save() {
        Mockito.when(userRepository.save(secondUser))
                .thenThrow(new UsernameAlreadyExistsException(
                        "User",
                        "username",
                        secondUser.getUsername()));

        final Exception exception = Assertions.assertThrows(
                UsernameAlreadyExistsException.class,
                () -> userService.save(secondUser));

        final String expectedMessage = "Entity 'User' with 'username' value 'marieCurie' already exists.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void should_Find_User_When_Id_Is_Valid() {
        final User searchedUser = userService.findById(secondUser.getId());

        Assertions.assertNotNull(searchedUser);
        Assertions.assertEquals("2", String.valueOf(searchedUser.getId()));
        Assertions.assertEquals(secondUser, searchedUser);
    }

    @Test
    public void should_Thrown_Exception_When_Id_Is_Not_Valid() {
        Mockito.when(userRepository.findById(firstUser.getId()))
                .thenReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.findById(firstUser.getId()));

        final String expectedMessage = "Entity 'User' with 'id' value '1' was not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void should_Find_User_When_Username_Not_Exists() {
        final User searchedUser = userService.findByUsername(secondUser.getUsername());

        Assertions.assertNotNull(searchedUser);
        Assertions.assertEquals("2", String.valueOf(searchedUser.getId()));
        Assertions.assertEquals(secondUser, searchedUser);
    }

    @Test
    public void should_Thrown_Exception_When_Username_Already_Exists() {
        Mockito.when(userRepository.findByUsername(firstUser.getUsername()))
                .thenReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.findByUsername(firstUser.getUsername()));

        final String expectedMessage = "Entity 'User' with 'username' value 'michael' was not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void should_Find_All_Users() {
        final List<User> searchedUsers = userService.findAll();

        Assertions.assertNotNull(searchedUsers);
        Assertions.assertEquals(users.size(), searchedUsers.size());
        Assertions.assertEquals(2, searchedUsers.size());
    }

    @Test
    public void should_Update_User_When_Username_Does_Not_Exists() {
        final User updatedUser = userService.update(secondUser, firstUser);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals("michael", updatedUser.getUsername());
    }

    @Test
    public void should_Update_User_When_Username_Is_Equals() {
        thirdUser.setUsername("michael");
        final User updatedUser = userService.update(thirdUser, firstUser);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals("michael", updatedUser.getUsername());
    }

    @Test
    public void should_Throw_Exception_When_Username_Already_Exists_Update() {
        Mockito.when(userRepository.save(secondUser))
                .thenThrow(new UsernameAlreadyExistsException(
                        "User",
                        "email",
                        secondUser.getEmail()));

        final Exception exception = Assertions.assertThrows(
                UsernameAlreadyExistsException.class,
                () -> userService.update(firstUser, secondUser));

        final String expectedMessage = "Entity 'User' with 'username' value 'marieCurie' already exists.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void should_Delete_User() {
        userService.delete(secondUser);

        Mockito.verify(userRepository, Mockito.times(1)).delete(secondUser);
        Assertions.assertEquals(2, users.size());
    }
}
