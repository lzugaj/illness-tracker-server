package com.luv2code.illnesstracker.service.impl.illness;

import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.illness.type.GastroEsophagealReflux;
import com.luv2code.illnesstracker.repository.illness.IllnessTypeRepository;
import com.luv2code.illnesstracker.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class GastroEsophagealRefluxServiceImpl extends AbstractIllnessTypeService<GastroEsophagealReflux> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GastroEsophagealRefluxServiceImpl.class);

    @Autowired
    public GastroEsophagealRefluxServiceImpl(final IllnessTypeRepository<GastroEsophagealReflux> illnessTypeRepository,
                                             final PatientService patientService) {
        super(illnessTypeRepository, patientService, GastroEsophagealReflux.class);
    }

    @Override
    public GastroEsophagealReflux save(final Patient patient, final GastroEsophagealReflux gastroEsophagealReflux) {
        setupVariablesCreate(patient, gastroEsophagealReflux);
        return super.save(patient, gastroEsophagealReflux);
    }

    @Override
    public GastroEsophagealReflux findById(final Long id) {
        return super.findById(id);
    }

    @Override
    public List<GastroEsophagealReflux> findAll() {
        return super.findAll();
    }

    @Override
    public List<GastroEsophagealReflux> findAllForPatient(final Patient patient) {
        return super.findAllForPatient(patient);
    }

    @Override
    public GastroEsophagealReflux update(final GastroEsophagealReflux oldGastroEsophagealReflux, final GastroEsophagealReflux newGastroEsophagealReflux) {
        setupVariablesUpdate(oldGastroEsophagealReflux, newGastroEsophagealReflux);
        return super.update(oldGastroEsophagealReflux, newGastroEsophagealReflux);
    }

    @Override
    public void delete(final GastroEsophagealReflux gastroEsophagealReflux) {
        super.delete(gastroEsophagealReflux);
    }

    private void setupVariablesCreate(final Patient patient, final GastroEsophagealReflux gastroEsophagealReflux) {
        LOGGER.info("Setting up new GastroEsophagealReflux variables.");
        gastroEsophagealReflux.setDateOfPerformedMeasurement(LocalDateTime.now());

        final List<GastroEsophagealReflux> gastroEsophagealRefluxes = findAllForPatient(patient);
        LOGGER.info("Successfully founded ´{}´ GastroEsophagealReflux record for Patient with id: ´{}´.", gastroEsophagealRefluxes.size(), patient.getId());

        gastroEsophagealRefluxes.add(gastroEsophagealReflux);
        gastroEsophagealReflux.setPatients(Collections.singletonList(patient));

        LOGGER.info("Setting up Patient variables.");
        patient.setGastroEsophagealRefluxes(gastroEsophagealRefluxes);
    }

    private void setupVariablesUpdate(final GastroEsophagealReflux oldGastroEsophagealReflux, final GastroEsophagealReflux newGastroEsophagealReflux) {
        LOGGER.info("Setting up old GastroEsophagealReflux variables.");
        oldGastroEsophagealReflux.setDatetimeOfLastMeal(newGastroEsophagealReflux.getDatetimeOfLastMeal());
        oldGastroEsophagealReflux.setDatetimeOfOnsetOfSymptoms(newGastroEsophagealReflux.getDatetimeOfOnsetOfSymptoms());
    }

}
