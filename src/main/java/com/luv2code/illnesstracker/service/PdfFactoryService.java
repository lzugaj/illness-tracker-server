package com.luv2code.illnesstracker.service;

import com.luv2code.illnesstracker.domain.User;

import java.io.ByteArrayInputStream;

public interface PdfFactoryService {

    ByteArrayInputStream generate(final User user, final String illnessName);

}
