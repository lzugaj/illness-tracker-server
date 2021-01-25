package com.luv2code.illnesstracker.service;

import com.luv2code.illnesstracker.domain.User;

public interface PdfNamingService {

    String generate(final User user, final String illnessType);

}
