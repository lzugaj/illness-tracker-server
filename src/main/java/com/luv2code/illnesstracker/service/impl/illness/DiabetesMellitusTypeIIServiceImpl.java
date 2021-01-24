package com.luv2code.illnesstracker.service.impl.illness;

import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.illness.type.DiabetesMellitusTypeII;
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
public class DiabetesMellitusTypeIIServiceImpl extends AbstractIllnessTypeService<DiabetesMellitusTypeII> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiabetesMellitusTypeIIServiceImpl.class);

    @Autowired
    public DiabetesMellitusTypeIIServiceImpl(final IllnessTypeRepository<DiabetesMellitusTypeII> illnessTypeRepository,
                                             final PatientService patientService) {
        super(illnessTypeRepository, patientService, DiabetesMellitusTypeII.class);
    }

    @Override
    public DiabetesMellitusTypeII save(final Patient patient, final DiabetesMellitusTypeII diabetesMellitusTypeII) {
        setupVariablesCreate(patient, diabetesMellitusTypeII);
        return super.save(patient, diabetesMellitusTypeII);
    }

    @Override
    public DiabetesMellitusTypeII findById(final Long id) {
        return super.findById(id);
    }

    @Override
    public List<DiabetesMellitusTypeII> findAll() {
        return super.findAll();
    }

    @Override
    public List<DiabetesMellitusTypeII> findAllForPatient(final Patient patient) {
        return super.findAllForPatient(patient);
    }

    @Override
    public DiabetesMellitusTypeII update(final DiabetesMellitusTypeII oldDiabetesMellitusTypeII, final DiabetesMellitusTypeII newDiabetesMellitusTypeII) {
        setupVariablesUpdate(oldDiabetesMellitusTypeII, newDiabetesMellitusTypeII);
        return super.update(oldDiabetesMellitusTypeII, newDiabetesMellitusTypeII);
    }

    @Override
    public void delete(final DiabetesMellitusTypeII diabetesMellitusTypeII) {
        super.delete(diabetesMellitusTypeII);
    }

    private void setupVariablesCreate(final Patient patient, final DiabetesMellitusTypeII diabetesMellitusTypeII) {
        LOGGER.info("Setting up new DiabetesMellitusTypeII variables.");
        diabetesMellitusTypeII.setDateOfPerformedMeasurement(LocalDateTime.now());

        final List<DiabetesMellitusTypeII> diabetesMellitusTypesII = findAllForPatient(patient);
        LOGGER.info("Successfully founded ´{}´ DiabetesMellitusTypeII record for Patient with id: ´{}´.", diabetesMellitusTypesII.size(), patient.getId());

        diabetesMellitusTypesII.add(diabetesMellitusTypeII);
        diabetesMellitusTypeII.setPatients(Collections.singletonList(patient));

        LOGGER.info("Setting up Patient variables.");
        patient.setDiabetesMellitusTypesII(diabetesMellitusTypesII);
    }

    private void setupVariablesUpdate(final DiabetesMellitusTypeII oldDiabetesMellitusTypeII, final DiabetesMellitusTypeII newDiabetesMellitusTypeII) {
        LOGGER.info("Setting up old DiabetesMellitusTypeII variables.");
        oldDiabetesMellitusTypeII.setGuk0(newDiabetesMellitusTypeII.getGuk0());
        oldDiabetesMellitusTypeII.setGuk1(newDiabetesMellitusTypeII.getGuk1());
    }
}
