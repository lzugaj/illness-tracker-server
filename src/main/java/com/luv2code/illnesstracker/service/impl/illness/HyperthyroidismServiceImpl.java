package com.luv2code.illnesstracker.service.impl.illness;

import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.illness.type.Hyperthyroidism;
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
public class HyperthyroidismServiceImpl extends AbstractIllnessTypeService<Hyperthyroidism> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HyperthyroidismServiceImpl.class);

    @Autowired
    public HyperthyroidismServiceImpl(final IllnessTypeRepository<Hyperthyroidism> illnessTypeRepository,
                                      final PatientService patientService) {
        super(illnessTypeRepository, patientService, Hyperthyroidism.class);
    }

    @Override
    public Hyperthyroidism save(final Patient patient, final Hyperthyroidism hyperthyroidism) {
        setupVariablesCreate(patient, hyperthyroidism);
        return super.save(patient, hyperthyroidism);
    }

    @Override
    public Hyperthyroidism findById(final Long id) {
        return super.findById(id);
    }

    @Override
    public List<Hyperthyroidism> findAll() {
        return super.findAll();
    }

    @Override
    public List<Hyperthyroidism> findAllForPatient(final Patient patient) {
        return super.findAllForPatient(patient);
    }

    @Override
    public Hyperthyroidism update(final Hyperthyroidism oldHyperthyroidism, final Hyperthyroidism newHyperthyroidism) {
        setupVariablesUpdate(oldHyperthyroidism, newHyperthyroidism);
        return super.update(oldHyperthyroidism, newHyperthyroidism);
    }

    @Override
    public void delete(final Hyperthyroidism illness) {
        super.delete(illness);
    }

    private void setupVariablesCreate(final Patient patient, final Hyperthyroidism hyperthyroidism) {
        LOGGER.info("Setting up new Hyperthyroidism variables.");
        hyperthyroidism.setDateOfPerformedMeasurement(LocalDateTime.now());

        final List<Hyperthyroidism> hyperthyroid = findAllForPatient(patient);
        LOGGER.info("Successfully founded ´{}´ Hyperthyroidism record for Patient with id: ´{}´.", hyperthyroid.size(), patient.getId());

        hyperthyroid.add(hyperthyroidism);
        hyperthyroidism.setPatients(Collections.singletonList(patient));

        LOGGER.info("Setting up Patient variables.");
        patient.setHyperthyroid(hyperthyroid);
    }

    private void setupVariablesUpdate(final Hyperthyroidism oldHyperthyroidism, final Hyperthyroidism newHyperthyroidism) {
        LOGGER.info("Setting up old Hyperthyroidism variables.");
        oldHyperthyroidism.setTsh(newHyperthyroidism.getTsh());
        oldHyperthyroidism.setFt3(newHyperthyroidism.getFt3());
        oldHyperthyroidism.setFt4(newHyperthyroidism.getFt4());
    }
}
