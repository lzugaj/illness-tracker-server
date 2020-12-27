package com.luv2code.illnesstracker.service;

import com.luv2code.illnesstracker.domain.Patient;

public interface PdfNamingService {

    String generate(final Patient patient, final String illnessType);

}
