package com.luv2code.illnesstracker.controller;

import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.illness.type.Hyperthyroidism;
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
@RequestMapping("/hyperthyroid")
public class HyperthyroidismController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HyperthyroidismController.class);

    private static final String HYPERTHYROIDISM = "hyperthyroidism";
    private static final String PDF_RESOURCE_FOLDER_PATH = "C:\\Workspace\\illness-tracker\\illness-tracker-server\\src\\main\\resources\\pdf\\";

    private final IllnessTypeService<Hyperthyroidism> illnessTypeService;

    private final PatientService patientService;

    private final PdfNamingService pdfNamingService;

    private final PdfFactoryService pdfFactoryService;

    @Autowired
    public HyperthyroidismController(@Qualifier("hyperthyroidismServiceImpl") final IllnessTypeService<Hyperthyroidism> illnessTypeService,
                                     final PatientService patientService,
                                     final PdfNamingService pdfNamingService,
                                     final PdfFactoryService pdfFactoryService) {
        this.illnessTypeService = illnessTypeService;
        this.patientService = patientService;
        this.pdfNamingService = pdfNamingService;
        this.pdfFactoryService = pdfFactoryService;
    }

    @PostMapping("/patient/{username}")
    public ResponseEntity<?> save(@PathVariable final String username, @Valid @RequestBody final Hyperthyroidism hyperthyroidism) {
        final Patient patient = patientService.findByUsername(username);
        LOGGER.info("Successfully founded Patient with username: ´{}´.", username);

        final Hyperthyroidism newHyperthyroidism = illnessTypeService.save(patient, hyperthyroidism);
        LOGGER.info("Successfully save new Hyperthyroidism with id: ´{}´.", hyperthyroidism.getId());
        return new ResponseEntity<>(newHyperthyroidism, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable final Long id) {
        final Hyperthyroidism searchedHyperthyroidism = illnessTypeService.findById(id);
        LOGGER.info("Successfully founded Hyperthyroidism with id: ´{}´.", id);
        return new ResponseEntity<>(searchedHyperthyroidism, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        final List<Hyperthyroidism> hyperthyroid = illnessTypeService.findAll();
        LOGGER.info("Successfully founded all Hyperthyroidism.");
        return new ResponseEntity<>(hyperthyroid, HttpStatus.OK);
    }

    @GetMapping("/patient/{username}")
    public ResponseEntity<?> findAllForPatient(@PathVariable final String username) {
        final Patient patient = patientService.findByUsername(username);
        LOGGER.info("Successfully founded Patient with username: ´{}´.", username);

        final List<Hyperthyroidism> hyperthyroid = illnessTypeService.findAllForPatient(patient);
        LOGGER.info("Successfully founded all Hyperthyroidism for Patient with username: ´{}´.", username);
        return new ResponseEntity<>(hyperthyroid, HttpStatus.OK);
    }

    @GetMapping("/patient/{username}/download/report")
    public ResponseEntity<?> generateReport(@PathVariable final String username) throws IOException {
        final Patient patient = patientService.findByUsername(username);
        LOGGER.info("Successfully founded Patient with username: ´{}´.", username);

        final String pdfReportName = pdfNamingService.generate(patient, HYPERTHYROIDISM);
        LOGGER.info("Successfully generated Hyperthyroidism pdf name: ´{}´.", pdfReportName);

        final ByteArrayInputStream bis = pdfFactoryService.generate(patient, IllnessTypeUtil.HYPERTHYROIDISM);
        LOGGER.info("Successfully generated Hyperthyroidism pdf file for Patient with username: ´{}´.", username);

        // TODO: Refactor
        final File file = new File( PDF_RESOURCE_FOLDER_PATH + pdfReportName + ".pdf");
        IOUtils.copy(bis, new FileOutputStream(file));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @Valid @RequestBody final Hyperthyroidism newHyperthyroidism) {
        final Hyperthyroidism oldHyperthyroidism = illnessTypeService.findById(id);
        LOGGER.info("Successfully founded Hyperthyroidism with id: ´{}´.", id);

        final Hyperthyroidism updatedHyperthyroidism = illnessTypeService.update(oldHyperthyroidism, newHyperthyroidism);
        LOGGER.info("Successfully updated Hyperthyroidism with id: ´{}´.", id);
        return new ResponseEntity<>(updatedHyperthyroidism, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        final Hyperthyroidism searchedHyperthyroidism = illnessTypeService.findById(id);
        LOGGER.info("Successfully founded Hyperthyroidism with id: ´{}´.", id);

        illnessTypeService.delete(searchedHyperthyroidism);
        LOGGER.info("Successfully delete Hyperthyroidism with id: ´{}´.", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
