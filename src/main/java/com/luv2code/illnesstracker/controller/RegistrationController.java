package com.luv2code.illnesstracker.controller;

import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.dto.PatientDto;
import com.luv2code.illnesstracker.dto.mapper.PatientMapper;
import com.luv2code.illnesstracker.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
public class
RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    private final PatientService patientService;

    private final PatientMapper patientMapper;

    @Autowired
    public RegistrationController(final PatientService patientService,
                                  final PatientMapper patientMapper) {
        this.patientService = patientService;
        this.patientMapper = patientMapper;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody final Patient patient) {
        final Patient newPatient = patientService.save(patient);
        LOGGER.info("Successfully created new Patient with id: ´{}´.", newPatient.getId());

        final PatientDto dto = patientMapper.toPatientDto(newPatient);
        LOGGER.info("Successfully mapped Patient to DTO.");

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
