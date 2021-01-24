package com.luv2code.illnesstracker.controller;

import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.illness.type.DiabetesMellitusTypeII;
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
@RequestMapping("/diabetes-mellitus-types-II")
public class DiabetesMellitusTypeIIController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiabetesMellitusTypeIIController.class);

    private static final String DIABETES_MELLITUS_TYPE_II = "dmtII";
    private static final String PDF_RESOURCE_FOLDER_PATH = "C:\\Workspace\\illness-tracker\\illness-tracker-server\\src\\main\\resources\\pdf\\";

    private final IllnessTypeService<DiabetesMellitusTypeII> illnessTypeService;

    private final PatientService patientService;

    private final PdfNamingService pdfNamingService;

    private final PdfFactoryService pdfFactoryService;

    @Autowired
    public DiabetesMellitusTypeIIController(@Qualifier("diabetesMellitusTypeIIServiceImpl") final IllnessTypeService<DiabetesMellitusTypeII> illnessTypeService,
                                     final PatientService patientService,
                                     final PdfNamingService pdfNamingService,
                                     final PdfFactoryService pdfFactoryService) {
        this.illnessTypeService = illnessTypeService;
        this.patientService = patientService;
        this.pdfNamingService = pdfNamingService;
        this.pdfFactoryService = pdfFactoryService;
    }

    @PostMapping("/patient/{username}")
    public ResponseEntity<?> save(@PathVariable final String username, @Valid @RequestBody final DiabetesMellitusTypeII diabetesMellitusTypeII) {
        final Patient patient = patientService.findByUsername(username);
        LOGGER.info("Successfully founded Patient with username: ´{}´.", username);

        final DiabetesMellitusTypeII newDiabetesMellitusTypeII = illnessTypeService.save(patient, diabetesMellitusTypeII);
        LOGGER.info("Successfully save new DiabetesMellitusTypeII with id: ´{}´.", diabetesMellitusTypeII.getId());
        return new ResponseEntity<>(newDiabetesMellitusTypeII, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable final Long id) {
        final DiabetesMellitusTypeII searchedDiabetesMellitusTypeII = illnessTypeService.findById(id);
        LOGGER.info("Successfully founded DiabetesMellitusTypeII with id: ´{}´.", id);
        return new ResponseEntity<>(searchedDiabetesMellitusTypeII, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        final List<DiabetesMellitusTypeII> diabetesMellitusTypesII = illnessTypeService.findAll();
        LOGGER.info("Successfully founded all DiabetesMellitusTypeII.");
        return new ResponseEntity<>(diabetesMellitusTypesII, HttpStatus.OK);
    }

    @GetMapping("/patient/{username}")
    public ResponseEntity<?> findAllForPatient(@PathVariable final String username) {
        final Patient patient = patientService.findByUsername(username);
        LOGGER.info("Successfully founded Patient with id: ´{}´.", username);

        final List<DiabetesMellitusTypeII> diabetesMellitusTypesII = illnessTypeService.findAllForPatient(patient);
        LOGGER.info("Successfully founded all DiabetesMellitusTypeII for Patient with id: ´{}´.", username);
        return new ResponseEntity<>(diabetesMellitusTypesII, HttpStatus.OK);
    }

    @GetMapping("/patient/{username}/download/report")
    public ResponseEntity<?> generateReport(@PathVariable final String username) throws IOException {
        final Patient patient = patientService.findByUsername(username);
        LOGGER.info("Successfully founded Patient with id: ´{}´.", username);

        final String pdfReportName = pdfNamingService.generate(patient, DIABETES_MELLITUS_TYPE_II);
        LOGGER.info("Successfully generated DiabetesMellitusTypeII pdf name: ´{}´.", pdfReportName);

        final ByteArrayInputStream bis = pdfFactoryService.generate(patient, IllnessTypeUtil.DIABETES_MELLITUS_TYPE_II);
        LOGGER.info("Successfully generated DiabetesMellitusTypeII pdf file for Patient with username: ´{}´.", username);

        // TODO: Refactor
        final File file = new File( PDF_RESOURCE_FOLDER_PATH + pdfReportName + ".pdf");
        IOUtils.copy(bis, new FileOutputStream(file));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @Valid @RequestBody final DiabetesMellitusTypeII newDiabetesMellitusTypeII) {
        final DiabetesMellitusTypeII oldDiabetesMellitusTypeII = illnessTypeService.findById(id);
        LOGGER.info("Successfully founded DiabetesMellitusTypeII with id: ´{}´.", id);

        final DiabetesMellitusTypeII updatedDiabetesMellitusTypeII = illnessTypeService.update(oldDiabetesMellitusTypeII, newDiabetesMellitusTypeII);
        LOGGER.info("Successfully updated DiabetesMellitusTypeII with id: ´{}´.", id);
        return new ResponseEntity<>(updatedDiabetesMellitusTypeII, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        final DiabetesMellitusTypeII searchedDiabetesMellitusTypeII = illnessTypeService.findById(id);
        LOGGER.info("Successfully founded DiabetesMellitusTypeII with id: ´{}´.", id);

        illnessTypeService.delete(searchedDiabetesMellitusTypeII);
        LOGGER.info("Successfully delete DiabetesMellitusTypeII with id: ´{}´.", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
