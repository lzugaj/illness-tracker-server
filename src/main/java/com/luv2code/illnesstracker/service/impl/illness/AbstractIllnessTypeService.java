package com.luv2code.illnesstracker.service.impl.illness;

import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.base.BaseIllness;
import com.luv2code.illnesstracker.exception.EntityNotFoundException;
import com.luv2code.illnesstracker.exception.IllnessOptionIsNotSelectedException;
import com.luv2code.illnesstracker.repository.illness.IllnessTypeRepository;
import com.luv2code.illnesstracker.service.IllnessTypeService;
import com.luv2code.illnesstracker.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public abstract class AbstractIllnessTypeService<T extends BaseIllness> implements IllnessTypeService<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractIllnessTypeService.class);

    private final Class<T> illnessClass;

    private final IllnessTypeRepository<T> illnessTypeRepository;

    private final PatientService patientService;

    @Autowired
    public AbstractIllnessTypeService(final IllnessTypeRepository<T> illnessTypeRepository,
                                      final PatientService patientService,
                                      final Class<T> illnessClass) {
        this.illnessTypeRepository = illnessTypeRepository;
        this.patientService = patientService;
        this.illnessClass = illnessClass;
    }

    @Override
    public T save(final Patient patient, final T illness) {
        if (patient.getIsBodyMassIndexActive()) {
            final T newIllness = illnessTypeRepository.save(illness);
            LOGGER.info("Saving new ´{}´ with id: ´{}´.", getIllnessName(illnessClass), illness.getId());
            return newIllness;
        } else {
            LOGGER.error("´{}´ is not active for Patient with id: ´{}´.", getIllnessName(illnessClass), patient.getId());
            throw new IllnessOptionIsNotSelectedException("Patient", "isActive", String.valueOf(patient.getIsBodyMassIndexActive()));
        }
    }

    @Override
    public T findById(final Long id) {
        final Optional<T> searchedIllness = illnessTypeRepository.findById(id);
        if (searchedIllness.isPresent()) {
            LOGGER.info("Searching ´{}´ with id: ´{}´.", getIllnessName(illnessClass), id);
            return searchedIllness.get();
        } else {
            LOGGER.error("´{}´ was not founded with id: ´{}´.", getIllnessName(illnessClass), id);
            throw new EntityNotFoundException(getIllnessName(illnessClass), "id", String.valueOf(id));
        }
    }

    @Override
    public List<T> findAll() {
        final List<T> illnesses = illnessTypeRepository.findAll();
        LOGGER.info("Searching all ´{}´.", getIllnessName(illnessClass));
        return illnesses;
    }

    @Override
    public List<T> findAllForPatient(final Patient patient) {
        final List<T> illnesses = findAll();
        final List<T> searchedIllnesses = new ArrayList<>();
        LOGGER.info("Searching all ´{}´ for Patient with id: ´{}´.", getIllnessName(illnessClass), patient.getId());

        for (T illness : illnesses) {
            for (Patient searchedPatient : patientService.findAll()) {
                if (patient.getEmail().equals(searchedPatient.getEmail())) {
                    searchedIllnesses.add(illness);
                }
            }
        }

        return searchedIllnesses;
    }

    @Override
    public T update(final T oldIllness, final T newIllness) {
        illnessTypeRepository.save(oldIllness);
        LOGGER.info("Updating ´{}´ with id: ´{}´.", getIllnessName(illnessClass), oldIllness.getId());
        return oldIllness;
    }

    @Override
    public void delete(final T illness) {
        LOGGER.info("Deleting ´{}´ with id: ´{}´.", getIllnessName(illnessClass), illness.getId());
        illnessTypeRepository.delete(illness);
    }

    private String getIllnessName(final Class<T> illnessClass) {
        return illnessClass.getName().substring(43);
    }
}
