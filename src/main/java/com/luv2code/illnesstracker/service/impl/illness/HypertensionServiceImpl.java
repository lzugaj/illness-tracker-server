package com.luv2code.illnesstracker.service.impl.illness;

import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.illness.type.Hypertension;
import com.luv2code.illnesstracker.domain.info.HypertensionInfo;
import com.luv2code.illnesstracker.repository.illness.IllnessTypeRepository;
import com.luv2code.illnesstracker.service.IllnessTypeInfoService;
import com.luv2code.illnesstracker.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class HypertensionServiceImpl extends AbstractIllnessTypeService<Hypertension> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HypertensionServiceImpl.class);

    private final IllnessTypeInfoService<HypertensionInfo> illnessTypeInfoService;

    @Autowired
    public HypertensionServiceImpl(final IllnessTypeRepository<Hypertension> illnessTypeRepository,
                                   final PatientService patientService,
                                   @Qualifier("hypertensionInfoServiceImpl") final IllnessTypeInfoService<HypertensionInfo> illnessTypeInfoService) {
        super(illnessTypeRepository, patientService, Hypertension.class);
        this.illnessTypeInfoService = illnessTypeInfoService;
    }

    @Override
    public Hypertension save(final Patient patient, final Hypertension hypertension) {
        setupVariablesCreate(patient, hypertension);
        return super.save(patient, hypertension);
    }

    @Override
    public Hypertension findById(final Long id) {
        return super.findById(id);
    }

    @Override
    public List<Hypertension> findAll() {
        return super.findAll();
    }

    @Override
    public List<Hypertension> findAllForPatient(Patient patient) {
        return super.findAllForPatient(patient);
    }

    @Override
    public Hypertension update(final Hypertension oldHypertension, final Hypertension newHypertension) {
        setupVariablesUpdate(oldHypertension, newHypertension);
        return super.update(oldHypertension, newHypertension);
    }

    @Override
    public void delete(final Hypertension hypertension) {
        super.delete(hypertension);
    }

    private void setupVariablesCreate(final Patient patient, final Hypertension hypertension) {
        LOGGER.info("Setting up new Hypertension variables.");
        hypertension.setDateOfPerformedMeasurement(LocalDateTime.now());

        final List<Hypertension> hypertensives = findAllForPatient(patient);
        LOGGER.info("Successfully founded ´{}´ Hypertension record for Patient with id: ´{}´.", hypertensives.size(), patient.getId());

        hypertensives.add(hypertension);
        hypertension.setPatients(Collections.singletonList(patient));

        final HypertensionInfo hypertensionInfo = illnessTypeInfoService.findByIndexValue(Double.valueOf(hypertension.getSystolic()), Double.valueOf(hypertension.getDiastolic()));
        LOGGER.info("Successfully founded HypertensionInfo with id: ´{}´.", hypertensionInfo.getId());

        hypertension.setHypertensionInfo(hypertensionInfo);
        hypertensionInfo.setHypertension(Collections.singletonList(hypertension));

        LOGGER.info("Setting up Patient variables.");
        patient.setHypertension(hypertensives);
    }

    private void setupVariablesUpdate(final Hypertension oldHypertension, final Hypertension newHypertension) {
        LOGGER.info("Setting up old Hypertension variables.");
        oldHypertension.setSystolic(newHypertension.getSystolic());
        oldHypertension.setDiastolic(newHypertension.getDiastolic());

        final HypertensionInfo hypertensionInfo = illnessTypeInfoService.findByIndexValue(Double.valueOf(oldHypertension.getSystolic()), Double.valueOf(oldHypertension.getDiastolic()));
        LOGGER.info("Successfully founded HypertensionInfo with id: ´{}´.", hypertensionInfo.getId());

        oldHypertension.setHypertensionInfo(hypertensionInfo);
        hypertensionInfo.setHypertension(Collections.singletonList(oldHypertension));
    }
}
