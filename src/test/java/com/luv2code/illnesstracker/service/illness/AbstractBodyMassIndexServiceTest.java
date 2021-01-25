package com.luv2code.illnesstracker.service.illness;

import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.Role;
import com.luv2code.illnesstracker.domain.enums.GenderType;
import com.luv2code.illnesstracker.domain.illness.type.BodyMassIndex;
import com.luv2code.illnesstracker.domain.info.BodyMassIndexInfo;
import com.luv2code.illnesstracker.repository.illness.IllnessTypeRepository;
import com.luv2code.illnesstracker.service.UserService;
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

import static com.luv2code.illnesstracker.domain.enums.RoleType.USER;
import static com.luv2code.illnesstracker.domain.enums.StatusType.ACTIVE;

@SpringBootTest
public class AbstractBodyMassIndexServiceTest {

    @InjectMocks
    private BodyMassIndexServiceImpl bodyMassIndexService;

    @Mock
    private IllnessTypeRepository<BodyMassIndex> illnessTypeRepository;

    @Mock
    private UserService userService;

    private BodyMassIndex firstBodyMassIndex;

    @BeforeEach
    public void setup() {
        final Role userRole = new Role();
        userRole.setId(1L);
        userRole.setName(USER);
        userRole.setDescription("The user role can perform CRUD operations over his/her profile and illnesses.");

        final User user = new User();
        user.setFirstName("Michael");
        user.setLastName("Jordan");
        user.setEmail("michael.jordan23@gmail.com");
        user.setPassword("theGoat23");
        user.setDateOfBirth(LocalDate.of(1987, 10, 14));
        user.setPhoneNumber("+385918742236");
        user.setGender(GenderType.MALE);
        user.setDateOfRegistration(LocalDateTime.now());
        user.setStatus(ACTIVE);
        user.setRoles(Collections.singletonList(userRole));

        userRole.setUsers(Collections.singletonList(user));

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
        firstBodyMassIndex.setUsers(Collections.singletonList(user));

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
