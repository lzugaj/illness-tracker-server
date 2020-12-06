package com.luv2code.illnesstracker.service.impl;

import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.Role;
import com.luv2code.illnesstracker.exception.EmailAlreadyExistsException;
import com.luv2code.illnesstracker.exception.EntityNotFoundException;
import com.luv2code.illnesstracker.repository.PatientRepository;
import com.luv2code.illnesstracker.service.PatientService;
import com.luv2code.illnesstracker.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.luv2code.illnesstracker.domain.RoleType.PATIENT;

@Service
public class PatientServiceImpl implements PatientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientServiceImpl.class);

    private final PatientRepository patientRepository;

    private final RoleService roleService;

    @Autowired
    public PatientServiceImpl(final PatientRepository patientRepository,
                              final RoleService roleService) {
        this.patientRepository = patientRepository;
        this.roleService = roleService;
    }

    @Override
    public Patient save(final Patient patient) {
        if (doesEmailNotExists(patient)) {
            final Role role = roleService.findByName(PATIENT.name());
            LOGGER.info("Successfully founded Role with name: ´{}´.", role.getName());

            setupVariables(patient, role);

            final Patient newPatient = patientRepository.save(patient);
            LOGGER.info("Saving new Patient with id: ´{}´.", patient.getId());
            return newPatient;
        } else {
            LOGGER.error("Patient already exists with email: ´{}´.", patient.getEmail());
            throw new EmailAlreadyExistsException("Patient", "email", patient.getEmail());
        }
    }

    private boolean doesEmailNotExists(final Patient searchedPatient) {
        final List<Patient> patients = findAll();
        LOGGER.info("Searching Patient with email: ´{}´.", searchedPatient.getEmail());
        return patients.stream()
                .noneMatch(patient -> patient.getEmail().equals(searchedPatient.getEmail()));
    }

    private void setupVariables(final Patient patient, final Role role) {
        LOGGER.info("Setting up Patient variables.");
        patient.setDateOfRegistration(LocalDateTime.now());
        patient.setIsActive(true);
        patient.setRoles(Collections.singletonList(role));

        LOGGER.info("Setting up Role variables.");
        role.setPatients(Collections.singletonList(patient));
    }

    @Override
    public Patient findById(Long id) {
        final Optional<Patient> searchedPatient = patientRepository.findById(id);
        if (searchedPatient.isPresent()) {
            LOGGER.info("Searching Patient with id: ´{}´.", id);
            return searchedPatient.get();
        } else {
            LOGGER.error("Patient was not founded with id: ´{}´.", id);
            throw new EntityNotFoundException("Patient", "id", String.valueOf(id));
        }
    }

    @Override
    public List<Patient> findAll() {
        final List<Patient> patients = patientRepository.findAll();
        LOGGER.info("Searching all Patients.");
        return patients;
    }

    @Override
    public Patient update(final Patient oldPatient, final Patient newPatient) {
        setupVariables(oldPatient, newPatient);

        patientRepository.save(oldPatient);
        LOGGER.info("Updating Patient with id: ´{}´.", oldPatient.getId());
        return oldPatient;
    }

    private void setupVariables(final Patient oldPatient, final Patient newPatient) {
        LOGGER.info("Setting up Patient variables.");
        checkEmail(oldPatient, newPatient);

        oldPatient.setFirstName(newPatient.getFirstName());
        oldPatient.setLastName(newPatient.getLastName());
        oldPatient.setOib(newPatient.getOib());
        oldPatient.setDateOfBirth(newPatient.getDateOfBirth());
        oldPatient.setPhoneNumber(newPatient.getPhoneNumber());
        oldPatient.setGender(newPatient.getGender());
    }

    private void checkEmail(final Patient oldPatient, final Patient newPatient) {
        if (newPatient.getEmail().equals(oldPatient.getEmail())) {
            oldPatient.setEmail(newPatient.getEmail());
        } else {
            if (doesEmailNotExists(newPatient)) {
                oldPatient.setEmail(newPatient.getEmail());
            } else {
                LOGGER.error("Patient already exists with email: ´{}´.", newPatient.getEmail());
                throw new EmailAlreadyExistsException("Patient", "email", newPatient.getEmail());
            }
        }
    }

    @Override
    public void delete(final Patient patient) {
        LOGGER.info("Deleting Patient with id: ´{}´.", patient.getId());
        patientRepository.delete(patient);
    }
}
