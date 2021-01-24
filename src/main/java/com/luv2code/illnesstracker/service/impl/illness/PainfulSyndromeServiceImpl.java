package com.luv2code.illnesstracker.service.impl.illness;

import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.illness.type.PainfulSyndrome;
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
public class PainfulSyndromeServiceImpl extends AbstractIllnessTypeService<PainfulSyndrome> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PainfulSyndromeServiceImpl.class);

    @Autowired
    public PainfulSyndromeServiceImpl(final IllnessTypeRepository<PainfulSyndrome> illnessTypeRepository,
                                      final PatientService patientService) {
        super(illnessTypeRepository, patientService, PainfulSyndrome.class);
    }

    @Override
    public PainfulSyndrome save(final Patient patient, final PainfulSyndrome painfulSyndrome) {
        setupVariablesCreate(patient, painfulSyndrome);
        return super.save(patient, painfulSyndrome);
    }

    @Override
    public PainfulSyndrome findById(final Long id) {
        return super.findById(id);
    }

    @Override
    public List<PainfulSyndrome> findAll() {
        return super.findAll();
    }

    @Override
    public List<PainfulSyndrome> findAllForPatient(final Patient patient) {
        return super.findAllForPatient(patient);
    }

    @Override
    public PainfulSyndrome update(final PainfulSyndrome oldPainfulSyndrome, final PainfulSyndrome newPainfulSyndrome) {
        setupVariablesUpdate(oldPainfulSyndrome, newPainfulSyndrome);
        return super.update(oldPainfulSyndrome, newPainfulSyndrome);
    }

    @Override
    public void delete(final PainfulSyndrome painfulSyndrome) {
        super.delete(painfulSyndrome);
    }

    private void setupVariablesCreate(final Patient patient, final PainfulSyndrome painfulSyndrome) {
        LOGGER.info("Setting up new PainfulSyndrome variables.");
        painfulSyndrome.setDateOfPerformedMeasurement(LocalDateTime.now());

        final List<PainfulSyndrome> painfulSyndromes = findAllForPatient(patient);
        LOGGER.info("Successfully founded ´{}´ PainfulSyndrome record for Patient with id: ´{}´.", painfulSyndromes.size(), patient.getId());

        painfulSyndromes.add(painfulSyndrome);
        painfulSyndrome.setPatients(Collections.singletonList(patient));

        LOGGER.info("Setting up Patient variables.");
        patient.setPainfulSyndromes(painfulSyndromes);
    }

    private void setupVariablesUpdate(final PainfulSyndrome oldPainfulSyndrome, final PainfulSyndrome newPainfulSyndrome) {
        LOGGER.info("Setting up old PainfulSyndrome variables.");
        oldPainfulSyndrome.setBodyPart(newPainfulSyndrome.getBodyPart());
        oldPainfulSyndrome.setDescription(newPainfulSyndrome.getDescription());
        oldPainfulSyndrome.setVasValue(newPainfulSyndrome.getVasValue());
    }

}
