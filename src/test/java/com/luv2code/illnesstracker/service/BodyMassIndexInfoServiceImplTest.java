package com.luv2code.illnesstracker.service;

import com.luv2code.illnesstracker.domain.info.BodyMassIndexInfo;
import com.luv2code.illnesstracker.exception.EntityNotFoundException;
import com.luv2code.illnesstracker.repository.BodyMassIndexInfoRepository;
import com.luv2code.illnesstracker.service.impl.info.BodyMassIndexInfoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BodyMassIndexInfoServiceImplTest {

    @InjectMocks
    private BodyMassIndexInfoServiceImpl bodyMassIndexInfoService;

    @Mock
    private BodyMassIndexInfoRepository bodyMassIndexInfoRepository;

    private BodyMassIndexInfo firstBMIInfo;
    private BodyMassIndexInfo secondBMIInfo;
    private BodyMassIndexInfo thirdBMIInfo;

    private static final String NORMAL_WEIGHT = "Normal weight";

    @BeforeEach
    public void setup() {
        firstBMIInfo = new BodyMassIndexInfo();
        firstBMIInfo.setId(1L);
        firstBMIInfo.setValue("Below 18.5");
        firstBMIInfo.setClassification("Underweight");

        secondBMIInfo = new BodyMassIndexInfo();
        secondBMIInfo.setId(2L);
        secondBMIInfo.setValue("18.5-24.9");
        secondBMIInfo.setClassification("Normal weight");

        thirdBMIInfo = new BodyMassIndexInfo();
        thirdBMIInfo.setId(3L);
        thirdBMIInfo.setValue("25.0-29.9");
        thirdBMIInfo.setClassification("Overweight");

        Mockito.when(bodyMassIndexInfoRepository.findById(firstBMIInfo.getId())).thenReturn(java.util.Optional.ofNullable(firstBMIInfo));
        Mockito.when(bodyMassIndexInfoRepository.findByClassification(secondBMIInfo.getClassification())).thenReturn(java.util.Optional.ofNullable(secondBMIInfo));
    }

    @Test
    public void should_Return_BodyMassIndexInfo_When_Id_Is_Valid() {
        final BodyMassIndexInfo bodyMassIndexInfo = bodyMassIndexInfoService.findById(firstBMIInfo.getId());

        Assertions.assertNotNull(bodyMassIndexInfo);
        Assertions.assertEquals("1", String.valueOf(bodyMassIndexInfo.getId()));
        Assertions.assertEquals(firstBMIInfo, bodyMassIndexInfo);
    }

    @Test
    public void should_Throw_Exception_When_Id_Is_Not_Valid() {
        Mockito.when(bodyMassIndexInfoRepository.findById(thirdBMIInfo.getId()))
                .thenThrow(new EntityNotFoundException(
                        "BodyMassIndexInfo",
                        "id",
                        String.valueOf(thirdBMIInfo.getId())));

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> bodyMassIndexInfoService.findById(thirdBMIInfo.getId()));

        final String expectedMessage = "Entity 'BodyMassIndexInfo' with 'id' value '3' was not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_BodyMassIndexInfo_When_Classification_Is_Valid() {
        final BodyMassIndexInfo bodyMassIndexInfo = bodyMassIndexInfoService.findByClassification(secondBMIInfo.getClassification());

        Assertions.assertNotNull(bodyMassIndexInfo);
        Assertions.assertEquals("Normal weight", bodyMassIndexInfo.getClassification());
        Assertions.assertEquals(secondBMIInfo, bodyMassIndexInfo);
    }

    @Test
    public void should_Throw_Exception_When_Classification_Is_Not_Valid() {
        Mockito.when(bodyMassIndexInfoRepository.findByClassification(thirdBMIInfo.getClassification()))
                .thenThrow(new EntityNotFoundException(
                        "BodyMassIndexInfo",
                        "classification",
                        thirdBMIInfo.getClassification()));

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> bodyMassIndexInfoService.findByClassification(thirdBMIInfo.getClassification()));

        final String expectedMessage = "Entity 'BodyMassIndexInfo' with 'classification' value 'Overweight' was not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_BodyMassIndexInfo_By_Index_Value() {
        final BodyMassIndexInfo bodyMassIndexInfo = bodyMassIndexInfoService.findByClassification(NORMAL_WEIGHT);

        Assertions.assertNotNull(bodyMassIndexInfo);
        Assertions.assertEquals(NORMAL_WEIGHT, bodyMassIndexInfo.getClassification());
    }
}