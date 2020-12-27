package com.luv2code.illnesstracker.service;

import com.luv2code.illnesstracker.domain.Patient;

import java.io.ByteArrayInputStream;

public interface PdfFactoryService {

    ByteArrayInputStream generate(final Patient patient, final String illnessName);

}
