package com.luv2code.illnesstracker.controller;

import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.dto.PatientDto;
import com.luv2code.illnesstracker.dto.mapper.PatientMapper;
import com.luv2code.illnesstracker.service.PatientService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/patients")
@Api("Patient Controller")
public class PatientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

    private final PatientService patientService;

    private final PatientMapper patientMapper;

    @Autowired
    public PatientController(final PatientService patientService,
                             final PatientMapper patientMapper) {
        this.patientService = patientService;
        this.patientMapper = patientMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable final Long id) {
        final Patient searchedPatient = patientService.findById(id);
        LOGGER.info("Successfully founded ´Patient´ with id: ´{}´.", id);

        final PatientDto dto = patientMapper.toPatientDto(searchedPatient);
        LOGGER.info("Successfully mapped ´Patient´ to DTO.");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        final List<Patient> patients = patientService.findAll();
        LOGGER.info("Successfully founded all ´Patient´.");

        final List<PatientDto> dto = patientMapper.toPatientsDto(patients);
        LOGGER.info("Successfully mapped ´Patient´ to DTO.");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @Valid @RequestBody final Patient newPatient) {
        final Patient searchedPatient = patientService.findById(id);
        LOGGER.info("Successfully founded ´Patient´ with id: ´{}´.", id);

        final Patient updatedPatient = patientService.update(searchedPatient, newPatient);
        LOGGER.info("Successfully updated ´Patient´ with id: ´{}´.", id);

        final PatientDto dto = patientMapper.toPatientDto(updatedPatient);
        LOGGER.info("Successfully mapped ´Patient´ to DTO.");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        final Patient searchedPatient = patientService.findById(id);
        LOGGER.info("Successfully founded ´Patient´ with id: ´{}´.", id);

        patientService.delete(searchedPatient);
        LOGGER.info("Successfully deleted ´Patient´ with id: ´{}´.", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
