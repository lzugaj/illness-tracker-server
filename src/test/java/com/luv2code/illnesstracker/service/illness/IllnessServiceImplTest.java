package com.luv2code.illnesstracker.service.illness;

import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.Role;
import com.luv2code.illnesstracker.domain.illness.Illness;
import com.luv2code.illnesstracker.repository.illness.IllnessRepository;
import com.luv2code.illnesstracker.service.UserService;
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
import static com.luv2code.illnesstracker.domain.enums.RoleType.USER;

@SpringBootTest
public class IllnessServiceImplTest {

    @InjectMocks
    private IllnessServiceImpl illnessService;

    @Mock
    private IllnessRepository illnessRepository;

    @Mock
    private UserService userService;

    private Illness firstIllness;
    private Illness secondIllness;
    private Illness thirdIllness;

    private User user;

    private List<Illness> illnesses;

    @BeforeEach
    public void setup() {
        Role userRole = new Role();
        userRole.setId(1L);
        userRole.setName(USER);
        userRole.setDescription("The user role can perform CRUD operations over his/her profile and illnesses.");

        user = new User();
        user.setId(1L);
        user.setFirstName("Michael");
        user.setLastName("Jordan");
        user.setEmail("michael.jordan23@gmail.com");
        user.setUsername("michael");
        user.setPassword("TheGoat23");
        user.setDateOfBirth(LocalDate.of(1987, 4, 15));
        user.setPhoneNumber("+385912346789");
        user.setGender(MALE);
        user.setBodyMassIndexes(null);
        user.setHypertension(null);
        user.setHyperthyroid(null);
        user.setDiabetesMellitusTypesII(null);
        user.setPainfulSyndromes(null);
        user.setGastroEsophagealRefluxes(null);
        user.setIsBodyMassIndexActive(false);
        user.setIsHypertensionActive(false);
        user.setIsHyperthyroidismActive(false);

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
        Mockito.doNothing().when(userService.update(user, user));
    }

    @Test
    public void should_Select_Illnesses() {
        final List<Illness> chosenIllnesses = illnessService.select(user, illnesses);

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
