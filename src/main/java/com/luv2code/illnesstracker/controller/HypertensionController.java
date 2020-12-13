package com.luv2code.illnesstracker.controller;

import com.luv2code.illnesstracker.domain.illness.Hypertension;
import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.service.HypertensionService;
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
@RequestMapping("/hypertensive")
public class HypertensionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HypertensionController.class);

    private final HypertensionService hypertensionService;

    private final PatientService patientService;

    @Autowired
    public HypertensionController(final HypertensionService hypertensionService,
                                   final PatientService patientService) {
        this.hypertensionService = hypertensionService;
        this.patientService = patientService;
    }

    @PostMapping("/patient/{id}")
    public ResponseEntity<?> save(@PathVariable Long id, @Valid @RequestBody final Hypertension hypertension) {
        final Patient patient = patientService.findById(id);
        LOGGER.info("Successfully founded Patient with id: ´{}´.", id);

        final Hypertension newHypertension = hypertensionService.save(patient, hypertension);
        LOGGER.info("Successfully save new Hypertension with id: ´{}´.", hypertension.getId());
        return new ResponseEntity<>(newHypertension, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable final Long id) {
        final Hypertension searchedHypertension = hypertensionService.findById(id);
        LOGGER.info("Successfully founded Hypertension with id: ´{}´.", id);
        return new ResponseEntity<>(searchedHypertension, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        final List<Hypertension> hypertensive = hypertensionService.findAll();
        LOGGER.info("Successfully founded all Hypertension.");
        return new ResponseEntity<>(hypertensive, HttpStatus.OK);
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<?> findAllForPatient(@PathVariable final Long id) {
        final Patient patient = patientService.findById(id);
        LOGGER.info("Successfully founded Patient with id: ´{}´.", id);

        final List<Hypertension> hypertensive = hypertensionService.findAllForPatient(patient);
        LOGGER.info("Successfully founded all Hypertension for Patient with id: ´{}´.", id);
        return new ResponseEntity<>(hypertensive, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @Valid @RequestBody final Hypertension newHypertension) {
        final Hypertension oldHypertension = hypertensionService.findById(id);
        LOGGER.info("Successfully founded Hypertension with id: ´{}´.", id);

        final Hypertension updatedHypertension = hypertensionService.update(oldHypertension, newHypertension);
        LOGGER.info("Successfully updated Hypertension with id: ´{}´.", id);
        return new ResponseEntity<>(updatedHypertension, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        final Hypertension searchedHypertension = hypertensionService.findById(id);
        LOGGER.info("Successfully founded Hypertension with id: ´{}´.", id);

        hypertensionService.delete(searchedHypertension);
        LOGGER.info("Successfully delete Hypertension with id: ´{}´.", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
