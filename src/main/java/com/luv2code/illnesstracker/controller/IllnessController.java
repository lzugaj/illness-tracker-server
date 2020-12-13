package com.luv2code.illnesstracker.controller;

import com.luv2code.illnesstracker.domain.Illness;
import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.service.IllnessService;
import com.luv2code.illnesstracker.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/illnesses")
public class IllnessController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IllnessController.class);

    private final IllnessService illnessService;

    private final PatientService patientService;

    @Autowired
    public IllnessController(final IllnessService illnessService,
                             final PatientService patientService) {
        this.illnessService = illnessService;
        this.patientService = patientService;
    }

    @PostMapping("/patient/{id}")
    public ResponseEntity<?> select(@PathVariable final Long id, @RequestBody final List<Illness> illnesses) {
        final Patient searchedPatient = patientService.findById(id);
        LOGGER.info("Successfully founded Patient with id: ´{}´.", id);

        final List<Illness> searchedIllnesses = illnessService.select(searchedPatient, illnesses);
        LOGGER.info("Successfully selected and set visible chosen Illness for Patient with id: ´{}´.", id);
        return new ResponseEntity<>(searchedIllnesses, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        final List<Illness> illnesses = illnessService.findAll();
        LOGGER.info("Successfully founded all Illnesses.");
        return new ResponseEntity<>(illnesses, HttpStatus.OK);
    }
}
