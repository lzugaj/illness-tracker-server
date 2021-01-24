package com.luv2code.illnesstracker.service.impl.formatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class FormatterServiceImplTest {

    @InjectMocks
    private FormatterServiceImpl formatterService;

    private LocalDateTime firstCurrentDateTime;
    private LocalDateTime secondCurrentDateTime;
    private LocalDateTime thirdCurrentDateTime;
    private LocalDateTime fourthCurrentDateTime;

    @BeforeEach
    public void setup() {
        firstCurrentDateTime = LocalDateTime.of(2021, 1, 20, 8, 24);
        secondCurrentDateTime = LocalDateTime.of(2021, 2, 12, 18, 49);
        thirdCurrentDateTime = LocalDateTime.of(2021, 5, 23, 11, 8);
        fourthCurrentDateTime = LocalDateTime.of(2021, 7, 8, 22, 34);
    }

    @Test
    public void should_Format_Date() {
        final String formattedDate = formatterService.formatDate(firstCurrentDateTime);

        Assertions.assertEquals("20.1.2021", formattedDate);
    }

    @Test
    public void should_Format_Time_When_Hours_Are_Less_Then_10() {
        final String formattedTime = formatterService.formatTime(firstCurrentDateTime);

        Assertions.assertEquals("08:24", formattedTime);
    }

    @Test
    public void should_Format_Time_When_Hours_Are_Greater_Then_10() {
        final String formattedTime = formatterService.formatTime(secondCurrentDateTime);

        Assertions.assertEquals("18:49", formattedTime);
    }

    @Test
    public void should_Format_Time_When_Minutes_Are_Less_Then_10() {
        final String formattedTime = formatterService.formatTime(thirdCurrentDateTime);

        Assertions.assertEquals("11:08", formattedTime);
    }

    @Test
    public void should_Format_Time_When_Minutes_Are_Greater_Then_10() {
        final String formattedTime = formatterService.formatTime(fourthCurrentDateTime);

        Assertions.assertEquals("22:34", formattedTime);
    }
}