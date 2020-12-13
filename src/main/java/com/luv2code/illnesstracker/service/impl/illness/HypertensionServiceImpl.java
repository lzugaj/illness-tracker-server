package com.luv2code.illnesstracker.service.impl.illness;

import com.luv2code.illnesstracker.domain.illness.Hypertension;
import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.info.HypertensionInfo;
import com.luv2code.illnesstracker.exception.EntityNotFoundException;
import com.luv2code.illnesstracker.exception.IllnessOptionIsNotSelectedException;
import com.luv2code.illnesstracker.repository.HypertensionRepository;
import com.luv2code.illnesstracker.service.HypertensionInfoService;
import com.luv2code.illnesstracker.service.HypertensionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class HypertensionServiceImpl implements HypertensionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HypertensionServiceImpl.class);

    private final HypertensionRepository hypertensionRepository;

    private final HypertensionInfoService hypertensionInfoService;

    @Autowired
    public HypertensionServiceImpl(final HypertensionRepository hypertensionRepository,
                                   final HypertensionInfoService hypertensionInfoService) {
        this.hypertensionRepository = hypertensionRepository;
        this.hypertensionInfoService = hypertensionInfoService;
    }

    @Override
    public Hypertension save(final Patient patient, final Hypertension hypertension) {
        if (patient.getIsHypertensionActive()) {
            setupVariablesCreate(patient, hypertension);

            final Hypertension newHypertension = hypertensionRepository.save(hypertension);
            LOGGER.info("Saving new Hypertension with id: ´{}´.", hypertension.getId());
            return newHypertension;
        } else {
            LOGGER.error("Hypertension is not active for Patient with id: ´{}´.", patient.getId());
            throw new IllnessOptionIsNotSelectedException("Patient", "isHypertensionActive", String.valueOf(patient.getIsHypertensionActive()));
        }
    }

    @Override
    public Hypertension findById(final Long id) {
        final Optional<Hypertension> searchedHypertension = hypertensionRepository.findById(id);
        if (searchedHypertension.isPresent()) {
            LOGGER.info("Searching Hypertension with id: ´{}´.", id);
            return searchedHypertension.get();
        } else {
            LOGGER.error("Hypertension was not founded with id: ´{}´.", id);
            throw new EntityNotFoundException("Hypertension", "id", String.valueOf(id));
        }
    }

    @Override
    public List<Hypertension> findAll() {
        final List<Hypertension> hypertensives = hypertensionRepository.findAll();
        LOGGER.info("Searching all Hypertensives.");
        return hypertensives;
    }

    @Override
    public List<Hypertension> findAllForPatient(Patient patient) {
        final List<Hypertension> hypertensives = findAll();
        final List<Hypertension> searchedHypertension = new ArrayList<>();

        LOGGER.info("Searching all Hypertension for Patient with id: ´{}´.", patient.getId());
        for (Hypertension hypertension : hypertensives) {
            for (Patient searchedPatient : hypertension.getPatients()) {
                if (patient.getEmail().equals(searchedPatient.getEmail())) {
                    searchedHypertension.add(hypertension);
                }
            }
        }

        return searchedHypertension;
    }

    @Override
    public Hypertension update(final Hypertension oldHypertension, final Hypertension newHypertension) {
        setupVariablesUpdate(oldHypertension, newHypertension);

        hypertensionRepository.save(oldHypertension);
        LOGGER.info("Updating Hypertension with id: ´{}´.", oldHypertension.getId());
        return oldHypertension;
    }

    @Override
    public void delete(final Hypertension hypertension) {
        LOGGER.info("Deleting Hypertension with id: ´{}´.", hypertension.getId());
        hypertensionRepository.delete(hypertension);
    }

    private void setupVariablesCreate(final Patient patient, final Hypertension hypertension) {
        LOGGER.info("Setting up Hypertension variables.");
        hypertension.setDateOfPerformedMeasurement(LocalDateTime.now());

        final List<Hypertension> hypertensives = findAllForPatient(patient);
        hypertensives.add(hypertension);
        hypertension.setPatients(Collections.singletonList(patient));

        final HypertensionInfo hypertensionInfo = hypertensionInfoService.findByIndexValue(hypertension.getSystolic(), hypertension.getDiastolic());
        hypertension.setHypertensionInfo(hypertensionInfo);
        hypertensionInfo.setHypertension(Collections.singletonList(hypertension));

        LOGGER.info("Setting up Patient variables.");
        patient.setHypertension(hypertensives);
    }

    private void setupVariablesUpdate(final Hypertension oldHypertension, final Hypertension newHypertension) {
        LOGGER.info("Setting up Hypertension variables.");
        oldHypertension.setSystolic(newHypertension.getSystolic());
        oldHypertension.setDiastolic(newHypertension.getDiastolic());

        final HypertensionInfo hypertensionInfo = hypertensionInfoService.findByIndexValue(oldHypertension.getSystolic(), oldHypertension.getDiastolic());
        oldHypertension.setHypertensionInfo(hypertensionInfo);
        hypertensionInfo.setHypertension(Collections.singletonList(oldHypertension));
    }
}
