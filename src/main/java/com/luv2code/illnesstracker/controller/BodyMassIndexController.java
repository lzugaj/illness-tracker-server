package com.luv2code.illnesstracker.controller;

import com.luv2code.illnesstracker.domain.BodyMassIndex;
import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.service.BodyMassIndexService;
import com.luv2code.illnesstracker.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/body-mass-indexes")
public class BodyMassIndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BodyMassIndexController.class);

    private final BodyMassIndexService bodyMassIndexService;

    private final PatientService patientService;

    @Autowired
    public BodyMassIndexController(final BodyMassIndexService bodyMassIndexService,
                                   final PatientService patientService) {
        this.bodyMassIndexService = bodyMassIndexService;
        this.patientService = patientService;
    }

    @PostMapping("/patient/{id}")
    public ResponseEntity<?> save(@PathVariable Long id, @Valid @RequestBody final BodyMassIndex bodyMassIndex) {
        final Patient patient = patientService.findById(id);

        final BodyMassIndex newBodyMassIndex = bodyMassIndexService.save(patient, bodyMassIndex);
        LOGGER.info("Successfully save new BodyMassIndex with id: ´{}´.", bodyMassIndex.getId());
        return new ResponseEntity<>(newBodyMassIndex, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable final Long id) {
        final BodyMassIndex searchedBodyMassIndex = bodyMassIndexService.findById(id);
        LOGGER.info("Successfully founded BodyMassIndex with id: ´{}´.", id);
        return new ResponseEntity<>(searchedBodyMassIndex, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        final List<BodyMassIndex> bodyMassIndexes = bodyMassIndexService.findAll();
        LOGGER.info("Successfully founded all BodyMassIndexes.");
        return new ResponseEntity<>(bodyMassIndexes, HttpStatus.OK);
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<?> findAllForPatient(@PathVariable final Long id) {
        final Patient patient = patientService.findById(1L);

        final List<BodyMassIndex> bodyMassIndexes = bodyMassIndexService.findAllForPatient(patient);
        LOGGER.info("Successfully founded all BodyMassIndexes for Patient with id: ´{}´.", id);
        return new ResponseEntity<>(bodyMassIndexes, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @Valid @RequestBody final BodyMassIndex newBodyMassIndex) {
        final BodyMassIndex oldBodyMassIndex = bodyMassIndexService.findById(id);
        LOGGER.info("Successfully founded BodyMassIndex with id: ´{}´.", id);

        final BodyMassIndex updatedBodyMassIndex = bodyMassIndexService.update(oldBodyMassIndex, newBodyMassIndex);
        LOGGER.info("Successfully updated BodyMassIndex with id: ´{}´.", id);
        return new ResponseEntity<>(updatedBodyMassIndex, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        final BodyMassIndex searchedBodyMassIndex = bodyMassIndexService.findById(id);
        LOGGER.info("Successfully founded BodyMassIndex with id: ´{}´.", id);

        bodyMassIndexService.delete(searchedBodyMassIndex);
        LOGGER.info("Successfully delete BodyMassIndex with id: ´{}´.", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
