package com.luv2code.illnesstracker.controller;

import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.illness.type.GastroEsophagealReflux;
import com.luv2code.illnesstracker.service.IllnessTypeService;
import com.luv2code.illnesstracker.service.PatientService;
import com.luv2code.illnesstracker.service.PdfFactoryService;
import com.luv2code.illnesstracker.service.PdfNamingService;
import com.luv2code.illnesstracker.util.IllnessTypeUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/gastro-esophageal-refluxes")
public class GastroEsophagealRefluxController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GastroEsophagealRefluxController.class);

    private static final String GER = "ger";
    private static final String PDF_RESOURCE_FOLDER_PATH = "C:\\Workspace\\illness-tracker\\illness-tracker-server\\src\\main\\resources\\pdf\\";

    private final IllnessTypeService<GastroEsophagealReflux> illnessTypeService;

    private final PatientService patientService;

    private final PdfNamingService pdfNamingService;

    private final PdfFactoryService pdfFactoryService;

    @Autowired
    public GastroEsophagealRefluxController(@Qualifier("gastroEsophagealRefluxServiceImpl") final IllnessTypeService<GastroEsophagealReflux> illnessTypeService,
                                     final PatientService patientService,
                                     final PdfNamingService pdfNamingService,
                                     final PdfFactoryService pdfFactoryService) {
        this.illnessTypeService = illnessTypeService;
        this.patientService = patientService;
        this.pdfNamingService = pdfNamingService;
        this.pdfFactoryService = pdfFactoryService;
    }

    @PostMapping("/patient/{username}")
    public ResponseEntity<?> save(@PathVariable final String username, @Valid @RequestBody final GastroEsophagealReflux gastroEsophagealReflux) {
        final Patient patient = patientService.findByUsername(username);
        LOGGER.info("Successfully founded Patient with username: ´{}´.", username);

        final GastroEsophagealReflux newGastroEsophagealReflux = illnessTypeService.save(patient, gastroEsophagealReflux);
        LOGGER.info("Successfully save new GastroEsophagealReflux with id: ´{}´.", gastroEsophagealReflux.getId());
        return new ResponseEntity<>(newGastroEsophagealReflux, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable final Long id) {
        final GastroEsophagealReflux searchedGastroEsophagealReflux = illnessTypeService.findById(id);
        LOGGER.info("Successfully founded GastroEsophagealReflux with id: ´{}´.", id);
        return new ResponseEntity<>(searchedGastroEsophagealReflux, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        final List<GastroEsophagealReflux> gastroEsophagealRefluxes = illnessTypeService.findAll();
        LOGGER.info("Successfully founded all GastroEsophagealReflux.");
        return new ResponseEntity<>(gastroEsophagealRefluxes, HttpStatus.OK);
    }

    @GetMapping("/patient/{username}")
    public ResponseEntity<?> findAllForPatient(@PathVariable final String username) {
        final Patient patient = patientService.findByUsername(username);
        LOGGER.info("Successfully founded Patient with username: ´{}´.", username);

        final List<GastroEsophagealReflux> gastroEsophagealRefluxes = illnessTypeService.findAllForPatient(patient);
        LOGGER.info("Successfully founded all GastroEsophagealReflux for Patient with username: ´{}´.", username);
        return new ResponseEntity<>(gastroEsophagealRefluxes, HttpStatus.OK);
    }

    @GetMapping("/patient/{username}/download/report")
    public ResponseEntity<?> generateReport(@PathVariable final String username) throws IOException {
        final Patient patient = patientService.findByUsername(username);
        LOGGER.info("Successfully founded Patient with username: ´{}´.", username);

        final String pdfReportName = pdfNamingService.generate(patient, GER);
        LOGGER.info("Successfully generated GastroEsophagealReflux pdf name: ´{}´.", pdfReportName);

        final ByteArrayInputStream bis = pdfFactoryService.generate(patient, IllnessTypeUtil.GASTRO_ESOPHAGEAL_REFLUX);
        LOGGER.info("Successfully generated GastroEsophagealReflux pdf file for Patient with username: ´{}´.", username);

        // TODO: Refactor - folder on phone
        final File file = new File( PDF_RESOURCE_FOLDER_PATH + pdfReportName + ".pdf");
        IOUtils.copy(bis, new FileOutputStream(file));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @Valid @RequestBody final GastroEsophagealReflux newGastroEsophagealReflux) {
        final GastroEsophagealReflux oldGastroEsophagealReflux = illnessTypeService.findById(id);
        LOGGER.info("Successfully founded GastroEsophagealReflux with id: ´{}´.", id);

        final GastroEsophagealReflux updatedGastroEsophagealReflux = illnessTypeService.update(oldGastroEsophagealReflux, newGastroEsophagealReflux);
        LOGGER.info("Successfully updated GastroEsophagealReflux with id: ´{}´.", id);
        return new ResponseEntity<>(updatedGastroEsophagealReflux, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        final GastroEsophagealReflux searchedGastroEsophagealReflux = illnessTypeService.findById(id);
        LOGGER.info("Successfully founded GastroEsophagealReflux with id: ´{}´.", id);

        illnessTypeService.delete(searchedGastroEsophagealReflux);
        LOGGER.info("Successfully delete PainfulSyndrome with id: ´{}´.", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}