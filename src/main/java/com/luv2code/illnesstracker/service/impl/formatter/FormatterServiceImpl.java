package com.luv2code.illnesstracker.service.impl.formatter;

import com.luv2code.illnesstracker.service.FormatterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FormatterServiceImpl implements FormatterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FormatterServiceImpl.class);

    @Override
    public String formatDate(final LocalDateTime localDateTime) {
        final Integer dayOfMonth = localDateTime.getDayOfMonth();
        final Integer month = localDateTime.getMonthValue();
        final Integer year = localDateTime.getYear();
        LOGGER.info("Formatting date with value: ´{}.{}.{}´.",
                dayOfMonth, month, year);

        return dayOfMonth + "." + month + "." + year;
    }

    @Override
    public String formatTime(final LocalDateTime localDateTime) {
        String hours;
        String minutes;
        if (localDateTime.getHour() < 10) {
            hours = "0" + localDateTime.getHour();
        } else {
            hours = String.valueOf(localDateTime.getHour());
        }

        if (localDateTime.getMinute() < 10) {
            minutes = "0" + localDateTime.getMinute();
        } else {
            minutes = String.valueOf(localDateTime.getMinute());
        }

        LOGGER.info("Formatting time with value: ´{}:{}´.", hours, minutes);
        return hours + ":" + minutes;
    }
}
