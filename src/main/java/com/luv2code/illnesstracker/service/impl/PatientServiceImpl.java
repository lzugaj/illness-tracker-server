package com.luv2code.illnesstracker.service.impl;

import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.Role;
import com.luv2code.illnesstracker.domain.enums.RoleType;
import com.luv2code.illnesstracker.exception.EntityNotFoundException;
import com.luv2code.illnesstracker.exception.UsernameAlreadyExistsException;
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

import static com.luv2code.illnesstracker.domain.enums.StatusType.ACTIVE;

@Service
public class PatientServiceImpl implements PatientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientServiceImpl.class);

    private static final Long PATIENT_ID = 1L;

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
        if (doesUsernameNotExists(patient)) {
            setupVariablesCreate(patient);
            LOGGER.info("Successfully setup variables for Patient with username: ´{}´.", patient.getUsername());

            final Patient newPatient = patientRepository.save(patient);
            LOGGER.info("Saving new Patient with id: ´{}´.", patient.getId());
            return newPatient;
        } else {
            LOGGER.error("Patient already exists with username: ´{}´.", patient.getUsername());
            throw new UsernameAlreadyExistsException("Patient", "username", patient.getUsername());
        }
    }

    private void setupVariablesCreate(final Patient patient) {
        LOGGER.info("Setting up variables for Patient with username: ´{}´.", patient.getUsername());
        patient.setDateOfRegistration(LocalDateTime.now());
        patient.setStatus(ACTIVE);
        patient.setIsBodyMassIndexActive(false);
        patient.setIsHypertensionActive(false);
        patient.setIsHyperthyroidismActive(false);
        patient.setIsDiabetesMellitusTypeIIActive(false);
        patient.setIsPainfulSyndromesActive(false);
        patient.setIsGastroEsophagealRefluxActive(false);

        final Role role = roleService.findByName(RoleType.PATIENT);
        LOGGER.info("Successfully founded Role with name: ´{}´.", role.getName());

        patient.setRoles(Collections.singletonList(role));

        LOGGER.info("Setting up Role variables for Patient with email: ´{}´.", patient.getEmail());
        role.setPatients(Collections.singletonList(patient));
    }

    @Override
    public Patient findById(Long id) {
        final Optional<Patient> searchedPatient = patientRepository.findById(id);
        if (searchedPatient.isPresent()) {
            LOGGER.info("Searching Patient with id: ´{}´.", id);
            return searchedPatient.get();
        } else {
            LOGGER.error("Not founded Patient with id: ´{}´.", id);
            throw new EntityNotFoundException("Patient", "id", String.valueOf(id));
        }
    }

    @Override
    public Patient findByUsername(final String username) {
        final Optional<Patient> searchedPatient = patientRepository.findByUsername(username);
        if (searchedPatient.isPresent()) {
            LOGGER.info("Searching Patient with username: ´{}´.", username);
            return searchedPatient.get();
        } else {
            LOGGER.error("Not founded Patient with username: ´{}´.", username);
            throw new EntityNotFoundException("Patient", "username", username);
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
        setupVariablesUpdate(oldPatient, newPatient);
        LOGGER.info("Successfully update variables for Patient with email: ´{}´.", oldPatient.getEmail());

        patientRepository.save(oldPatient);
        LOGGER.info("Updating Patient with id: ´{}´.", oldPatient.getId());
        return oldPatient;
    }

    private void setupVariablesUpdate(final Patient oldPatient, final Patient newPatient) {
        LOGGER.info("Updating variables for Patient with email: ´{}´.", oldPatient.getEmail());
        checkUsername(oldPatient, newPatient);
        oldPatient.setFirstName(newPatient.getFirstName());
        oldPatient.setLastName(newPatient.getLastName());
        oldPatient.setEmail(newPatient.getEmail());
        oldPatient.setDateOfBirth(newPatient.getDateOfBirth());
        oldPatient.setPhoneNumber(newPatient.getPhoneNumber());
        oldPatient.setGender(newPatient.getGender());
    }

    private void checkUsername(final Patient oldPatient, final Patient newPatient) {
        LOGGER.info("Checking if username already exists for Patient with id: ´{}´.", oldPatient.getId());
        if (newPatient.getUsername().equals(oldPatient.getUsername())) {
            oldPatient.setUsername(newPatient.getUsername());
        } else {
            if (doesUsernameNotExists(newPatient)) {
                oldPatient.setUsername(newPatient.getUsername());
            } else {
                LOGGER.error("Patient already exists with email: ´{}´.", newPatient.getUsername());
                throw new UsernameAlreadyExistsException("Patient", "username", newPatient.getUsername());
            }
        }
    }

    private boolean doesUsernameNotExists(final Patient searchedPatient) {
        final List<Patient> patients = findAll();
        LOGGER.info("Searching Patient with username: ´{}´.", searchedPatient.getUsername());
        return patients.stream()
                .noneMatch(patient -> patient.getUsername().equals(searchedPatient.getUsername()));
    }

    @Override
    public void delete(final Patient patient) {
        LOGGER.info("Deleting Patient with id: ´{}´.", patient.getId());
        patientRepository.delete(patient);
    }
}
