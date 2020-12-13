package com.luv2code.illnesstracker.service.impl.illness;

import com.luv2code.illnesstracker.domain.illness.BodyMassIndex;
import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.info.BodyMassIndexInfo;
import com.luv2code.illnesstracker.exception.EntityNotFoundException;
import com.luv2code.illnesstracker.exception.IllnessOptionIsNotSelectedException;
import com.luv2code.illnesstracker.repository.BodyMassIndexRepository;
import com.luv2code.illnesstracker.service.BodyMassIndexInfoService;
import com.luv2code.illnesstracker.service.BodyMassIndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BodyMassIndexServiceImpl implements BodyMassIndexService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BodyMassIndexServiceImpl.class);

    private final BodyMassIndexRepository bodyMassIndexRepository;

    private final BodyMassIndexInfoService bodyMassIndexInfoService;

    @Autowired
    public BodyMassIndexServiceImpl(final BodyMassIndexRepository bodyMassIndexRepository,
                                    final BodyMassIndexInfoService bodyMassIndexInfoService) {
        this.bodyMassIndexRepository = bodyMassIndexRepository;
        this.bodyMassIndexInfoService = bodyMassIndexInfoService;
    }

    @Override
    public BodyMassIndex save(final Patient patient, final BodyMassIndex bodyMassIndex) {
        if (patient.getIsBodyMassIndexActive()) {
            setupVariablesCreate(patient, bodyMassIndex);

            final BodyMassIndex newBodyMassIndex = bodyMassIndexRepository.save(bodyMassIndex);
            LOGGER.info("Saving new BodyMassIndex with id: ´{}´.", bodyMassIndex.getId());
            return newBodyMassIndex;
        } else {
            LOGGER.error("Body Mass Index is not active for Patient with id: ´{}´.", patient.getId());
            throw new IllnessOptionIsNotSelectedException("Patient", "isBodyMassIndexActive", String.valueOf(patient.getIsBodyMassIndexActive()));
        }
    }

    @Override
    public BodyMassIndex findById(Long id) {
        final Optional<BodyMassIndex> searchedBodyMassIndex = bodyMassIndexRepository.findById(id);
        if (searchedBodyMassIndex.isPresent()) {
            LOGGER.info("Searching BodyMassIndex with id: ´{}´.", id);
            return searchedBodyMassIndex.get();
        } else {
            LOGGER.error("BodyMassIndex was not founded with id: ´{}´.", id);
            throw new EntityNotFoundException("BodyMassIndex", "id", String.valueOf(id));
        }
    }

    @Override
    public List<BodyMassIndex> findAll() {
        final List<BodyMassIndex> bodyMassIndexes = bodyMassIndexRepository.findAll();
        LOGGER.info("Searching all BodyMassIndexes.");
        return bodyMassIndexes;
    }

    @Override
    public List<BodyMassIndex> findAllForPatient(final Patient patient) {
        final List<BodyMassIndex> bodyMassIndexes = findAll();
        final List<BodyMassIndex> searchedBodyMassIndexes = new ArrayList<>();

        LOGGER.info("Searching all BodyMassIndex for Patient with id: ´{}´.", patient.getId());
        for (BodyMassIndex bodyMassIndex : bodyMassIndexes) {
            for (Patient searchedPatient : bodyMassIndex.getPatients()) {
                if (patient.getEmail().equals(searchedPatient.getEmail())) {
                    searchedBodyMassIndexes.add(bodyMassIndex);
                }
            }
        }

        return searchedBodyMassIndexes;
    }

    @Override
    public BodyMassIndex update(final BodyMassIndex oldBodyMassIndex, final BodyMassIndex newBodyMassIndex) {
        setupVariablesUpdate(oldBodyMassIndex, newBodyMassIndex);

        bodyMassIndexRepository.save(oldBodyMassIndex);
        LOGGER.info("Updating BodyMassIndex with id: ´{}´.", oldBodyMassIndex.getId());
        return oldBodyMassIndex;
    }

    @Override
    public void delete(final BodyMassIndex bodyMassIndex) {
        LOGGER.info("Deleting BodyMassIndex with id: ´{}´.", bodyMassIndex.getId());
        bodyMassIndexRepository.delete(bodyMassIndex);
    }

    private void setupVariablesCreate(final Patient patient, final BodyMassIndex bodyMassIndex) {
        LOGGER.info("Setting up BodyMassIndex variables.");
        bodyMassIndex.setDateOfPerformedMeasurement(LocalDateTime.now());

        final List<BodyMassIndex> bodyMassIndexes = findAllForPatient(patient);
        bodyMassIndexes.add(bodyMassIndex);
        bodyMassIndex.setPatients(Collections.singletonList(patient));

        final Double bmiIndexValue = calculateBMIIndexValue(bodyMassIndex);
        bodyMassIndex.setIndexValue(bmiIndexValue);

        final BodyMassIndexInfo bodyMassIndexInfo = bodyMassIndexInfoService.findByIndexValue(bmiIndexValue);
        bodyMassIndex.setBodyMassIndexInfo(bodyMassIndexInfo);
        bodyMassIndexInfo.setBodyMassIndexes(Collections.singletonList(bodyMassIndex));

        LOGGER.info("Setting up Patient variables.");
        patient.setBodyMassIndexes(bodyMassIndexes);
    }

    private void setupVariablesUpdate(final BodyMassIndex oldBodyMassIndex, final BodyMassIndex newBodyMassIndex) {
        LOGGER.info("Setting up BodyMassIndex variables.");
        oldBodyMassIndex.setHeight(newBodyMassIndex.getHeight());
        oldBodyMassIndex.setWeight(newBodyMassIndex.getWeight());

        final Double bmiIndexValue = calculateBMIIndexValue(newBodyMassIndex);
        oldBodyMassIndex.setIndexValue(bmiIndexValue);

        final BodyMassIndexInfo bodyMassIndexInfo = bodyMassIndexInfoService.findByIndexValue(bmiIndexValue);
        oldBodyMassIndex.setBodyMassIndexInfo(bodyMassIndexInfo);
        bodyMassIndexInfo.setBodyMassIndexes(Collections.singletonList(oldBodyMassIndex));
    }

    private Double calculateBMIIndexValue(final BodyMassIndex bodyMassIndex) {
        final double bmiIndexValue = (bodyMassIndex.getWeight() / Math.pow(bodyMassIndex.getHeight(), 2)) * 10000;
        final BigDecimal bd = new BigDecimal(bmiIndexValue).setScale(1, RoundingMode.HALF_UP);
        LOGGER.info("Calculated BodyMassIndex values is: ´{}´.", bd);

        return bd.doubleValue();
    }
}
