package com.luv2code.illnesstracker.service;

import com.luv2code.illnesstracker.domain.Patient;

import java.util.List;

public interface PatientService {

    Patient save(final Patient patient);

    Patient findById(final Long id);

    Patient findByUsername(final String username);

    List<Patient> findAll();

    Patient update(final Patient oldPatient, final Patient newPatient);

    void delete(final Patient patient);

}
