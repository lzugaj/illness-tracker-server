package com.luv2code.illnesstracker.service;

import com.luv2code.illnesstracker.domain.BodyMassIndex;
import com.luv2code.illnesstracker.domain.Patient;

import java.util.List;

public interface BodyMassIndexService {

    BodyMassIndex save(final Patient patient, final BodyMassIndex bodyMassIndex);

    BodyMassIndex findById(final Long id);

    List<BodyMassIndex> findAll();

    List<BodyMassIndex> findAllForPatient(final Patient patient);

    BodyMassIndex update(final BodyMassIndex oldBodyMassIndex, final BodyMassIndex newBodyMassIndex);

    void delete(final BodyMassIndex bodyMassIndex);

}
