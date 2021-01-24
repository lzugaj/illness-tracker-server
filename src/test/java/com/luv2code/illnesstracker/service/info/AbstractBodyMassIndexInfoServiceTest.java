package com.luv2code.illnesstracker.service.info;

import com.luv2code.illnesstracker.domain.info.BodyMassIndexInfo;
import com.luv2code.illnesstracker.exception.EntityNotFoundException;
import com.luv2code.illnesstracker.repository.info.IllnessTypeInfoRepository;
import com.luv2code.illnesstracker.service.impl.info.BodyMassIndexInfoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AbstractBodyMassIndexInfoServiceTest {

    @InjectMocks
    private BodyMassIndexInfoServiceImpl bodyMassIndexInfoService;

    @Mock
    private IllnessTypeInfoRepository<BodyMassIndexInfo> illnessTypeInfoRepository;

    private BodyMassIndexInfo firstBMIInfo;
    private BodyMassIndexInfo secondBMIInfo;

    @BeforeEach
    public void setup() {
        firstBMIInfo = new BodyMassIndexInfo();
        firstBMIInfo.setId(1L);
        firstBMIInfo.setValue("Below 18.5");
        firstBMIInfo.setClassification("Underweight");

        secondBMIInfo = new BodyMassIndexInfo();
        secondBMIInfo.setId(2L);
        secondBMIInfo.setValue("30.0-34.9");
        secondBMIInfo.setClassification("Obesity class I");

        Mockito.when(illnessTypeInfoRepository.findById(firstBMIInfo.getId())).thenReturn(java.util.Optional.ofNullable(firstBMIInfo));
        Mockito.when(illnessTypeInfoRepository.findByClassification(firstBMIInfo.getClassification())).thenReturn(java.util.Optional.ofNullable(firstBMIInfo));
    }

    @Test
    public void should_Return_BMI_Info_When_Id_Is_Founded() {
        final BodyMassIndexInfo searchedBMIInfo = bodyMassIndexInfoService.findById(firstBMIInfo.getId());

        Assertions.assertEquals(firstBMIInfo, searchedBMIInfo);
        Assertions.assertEquals("1", String.valueOf(searchedBMIInfo.getId()));
    }

    @Test
    public void should_Throw_Exception_When_Id_Is_Not_Founded() throws EntityNotFoundException {
        Mockito.when(illnessTypeInfoRepository.findById(secondBMIInfo.getId()))
                .thenThrow(new EntityNotFoundException(
                        "BodyMassIndexInfo",
                        "id",
                        String.valueOf(secondBMIInfo.getId())));

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> bodyMassIndexInfoService.findById(secondBMIInfo.getId()));

        final String expectedMessage = "Entity 'BodyMassIndexInfo' with 'id' value '2' was not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_BMI_Info_When_Classification_Is_Founded() {
        final BodyMassIndexInfo searchedBMIInfo = bodyMassIndexInfoService.findByClassification(firstBMIInfo.getClassification());

        Assertions.assertEquals(firstBMIInfo, searchedBMIInfo);
        Assertions.assertEquals("Underweight", firstBMIInfo.getClassification());
    }

    @Test
    public void should_Throw_Exception_When_Classification_Is_Not_Founded() {
        Mockito.when(illnessTypeInfoRepository.findByClassification(secondBMIInfo.getClassification()))
                .thenThrow(new EntityNotFoundException(
                        "BodyMassIndexInfo",
                        "classification",
                        secondBMIInfo.getClassification()));

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> bodyMassIndexInfoService.findByClassification(secondBMIInfo.getClassification()));

        final String expectedMessage = "Entity 'BodyMassIndexInfo' with 'classification' value 'Obesity class I' was not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_BMI_When_Index_Value_Is_Founded() {
        final BodyMassIndexInfo searchedBMIInfo = bodyMassIndexInfoService.findByIndexValue(17.5);

        Assertions.assertEquals(firstBMIInfo, searchedBMIInfo);
        Assertions.assertEquals("Underweight", searchedBMIInfo.getClassification());
    }
}
