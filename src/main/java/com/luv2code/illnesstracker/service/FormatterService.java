package com.luv2code.illnesstracker.service;

import java.time.LocalDateTime;

public interface FormatterService {

    String formatDate(final LocalDateTime localDateTime);

    String formatTime(final LocalDateTime localDateTime);

}
