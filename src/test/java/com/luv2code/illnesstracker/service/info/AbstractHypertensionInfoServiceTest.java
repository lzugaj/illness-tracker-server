package com.luv2code.illnesstracker.service.info;

import com.luv2code.illnesstracker.domain.info.HypertensionInfo;
import com.luv2code.illnesstracker.exception.EntityNotFoundException;
import com.luv2code.illnesstracker.repository.info.IllnessTypeInfoRepository;
import com.luv2code.illnesstracker.service.impl.info.HypertensionInfoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AbstractHypertensionInfoServiceTest {

    @InjectMocks
    private HypertensionInfoServiceImpl hypertensionInfoService;

    @Mock
    private IllnessTypeInfoRepository<HypertensionInfo> illnessTypeInfoRepository;

    private HypertensionInfo firstHypertensionInfo;
    private HypertensionInfo secondHypertensionInfo;

    @BeforeEach
    public void setup() {
        firstHypertensionInfo = new HypertensionInfo();
        firstHypertensionInfo.setId(1L);
        firstHypertensionInfo.setSystolicRange("120-129");
        firstHypertensionInfo.setDiastolicRange("Less than 80");
        firstHypertensionInfo.setClassification("Elevated");

        secondHypertensionInfo = new HypertensionInfo();
        secondHypertensionInfo.setId(2L);
        secondHypertensionInfo.setSystolicRange("Higher than 180");
        secondHypertensionInfo.setDiastolicRange("Higher than 120");
        secondHypertensionInfo.setClassification("Hypertensive Crisis");

        Mockito.when(illnessTypeInfoRepository.findById(firstHypertensionInfo.getId())).thenReturn(java.util.Optional.ofNullable(firstHypertensionInfo));
        Mockito.when(illnessTypeInfoRepository.findByClassification(firstHypertensionInfo.getClassification())).thenReturn(java.util.Optional.ofNullable(firstHypertensionInfo));
    }

    @Test
    public void should_Return_Hypertension_Info_When_Id_Is_Founded() {
        final HypertensionInfo searchedHypertensionInfo = hypertensionInfoService.findById(firstHypertensionInfo.getId());

        Assertions.assertEquals(firstHypertensionInfo, searchedHypertensionInfo);
        Assertions.assertEquals("1", String.valueOf(searchedHypertensionInfo.getId()));
    }

    @Test
    public void should_Throw_Exception_When_Id_Is_Not_Founded() {
        Mockito.when(illnessTypeInfoRepository.findById(secondHypertensionInfo.getId()))
                .thenThrow(new EntityNotFoundException(
                        "HypertensionInfo",
                        "id",
                        String.valueOf(secondHypertensionInfo.getId())));

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> hypertensionInfoService.findById(secondHypertensionInfo.getId()));

        final String expectedMessage = "Entity 'HypertensionInfo' with 'id' value '2' was not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_BMI_Hypertension_When_Classification_Is_Founded() {
        final HypertensionInfo searchedHypertensionInfo = hypertensionInfoService.findByClassification(firstHypertensionInfo.getClassification());

        Assertions.assertEquals(firstHypertensionInfo, searchedHypertensionInfo);
        Assertions.assertEquals("Elevated", firstHypertensionInfo.getClassification());
    }

    @Test
    public void should_Throw_Exception_When_Classification_Is_Not_Founded() {
        Mockito.when(illnessTypeInfoRepository.findByClassification(secondHypertensionInfo.getClassification()))
                .thenThrow(new EntityNotFoundException(
                        "HypertensionInfo",
                        "classification",
                        secondHypertensionInfo.getClassification()));

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> hypertensionInfoService.findByClassification(secondHypertensionInfo.getClassification()));

        final String expectedMessage = "Entity 'HypertensionInfo' with 'classification' value 'Hypertensive Crisis' was not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_Hypertension_When_Index_Value_Is_Founded() {
        final HypertensionInfo searchedHypertensionInfo = hypertensionInfoService.findByIndexValue(124.0, 78.0);

        Assertions.assertEquals(firstHypertensionInfo, searchedHypertensionInfo);
        Assertions.assertEquals("Elevated", searchedHypertensionInfo.getClassification());
    }
}
