package com.luv2code.illnesstracker.service;

import com.luv2code.illnesstracker.domain.illness.Hypertension;
import com.luv2code.illnesstracker.domain.Patient;

import java.util.List;

public interface HypertensionService {

    Hypertension save(final Patient patient, final Hypertension hypertension);

    Hypertension findById(final Long id);

    List<Hypertension> findAll();

    List<Hypertension> findAllForPatient(final Patient patient);

    Hypertension update(final Hypertension oldHypertension, final Hypertension newHypertension);

    void delete(final Hypertension hypertension);

}
