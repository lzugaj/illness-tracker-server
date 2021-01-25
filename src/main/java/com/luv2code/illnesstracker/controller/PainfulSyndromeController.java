package com.luv2code.illnesstracker.controller;

import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.illness.type.PainfulSyndrome;
import com.luv2code.illnesstracker.service.IllnessTypeService;
import com.luv2code.illnesstracker.service.UserService;
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
@RequestMapping("/painful-syndromes")
public class PainfulSyndromeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PainfulSyndromeController.class);

    private static final String PS = "ps";
    private static final String PDF_RESOURCE_FOLDER_PATH = "C:\\Workspace\\illness-tracker\\illness-tracker-server\\src\\main\\resources\\pdf\\";

    private final IllnessTypeService<PainfulSyndrome> illnessTypeService;

    private final UserService userService;

    private final PdfNamingService pdfNamingService;

    private final PdfFactoryService pdfFactoryService;

    @Autowired
    public PainfulSyndromeController(@Qualifier("painfulSyndromeServiceImpl") final IllnessTypeService<PainfulSyndrome> illnessTypeService,
                                   final UserService userService,
                                   final PdfNamingService pdfNamingService,
                                   final PdfFactoryService pdfFactoryService) {
        this.illnessTypeService = illnessTypeService;
        this.userService = userService;
        this.pdfNamingService = pdfNamingService;
        this.pdfFactoryService = pdfFactoryService;
    }

    @PostMapping("/patient/{username}")
    public ResponseEntity<?> save(@PathVariable final String username, @Valid @RequestBody final PainfulSyndrome painfulSyndrome) {
        final User user = userService.findByUsername(username);
        LOGGER.info("Successfully founded Patient with username: ´{}´.", username);

        final PainfulSyndrome newPainfulSyndrome = illnessTypeService.save(user, painfulSyndrome);
        LOGGER.info("Successfully save new PainfulSyndrome with id: ´{}´.", painfulSyndrome.getId());
        return new ResponseEntity<>(newPainfulSyndrome, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable final Long id) {
        final PainfulSyndrome searchedPainfulSyndrome = illnessTypeService.findById(id);
        LOGGER.info("Successfully founded PainfulSyndrome with id: ´{}´.", id);
        return new ResponseEntity<>(searchedPainfulSyndrome, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        final List<PainfulSyndrome> painfulSyndromes = illnessTypeService.findAll();
        LOGGER.info("Successfully founded all BodyMassIndex.");
        return new ResponseEntity<>(painfulSyndromes, HttpStatus.OK);
    }

    @GetMapping("/patient/{username}")
    public ResponseEntity<?> findAllForPatient(@PathVariable final String username) {
        final User user = userService.findByUsername(username);
        LOGGER.info("Successfully founded Patient with username: ´{}´.", username);

        final List<PainfulSyndrome> painfulSyndromes = illnessTypeService.findAllForUser(user);
        LOGGER.info("Successfully founded all PainfulSyndrome for Patient with username: ´{}´.", username);
        return new ResponseEntity<>(painfulSyndromes, HttpStatus.OK);
    }

    @GetMapping("/patient/{username}/download/report")
    public ResponseEntity<?> generateReport(@PathVariable final String username) throws IOException {
        final User user = userService.findByUsername(username);
        LOGGER.info("Successfully founded Patient with username: ´{}´.", username);

        final String pdfReportName = pdfNamingService.generate(user, PS);
        LOGGER.info("Successfully generated PainfulSyndrome pdf name: ´{}´.", pdfReportName);

        final ByteArrayInputStream bis = pdfFactoryService.generate(user, IllnessTypeUtil.PAINFUL_SYNDROMES);
        LOGGER.info("Successfully generated PainfulSyndrome pdf file for Patient with username: ´{}´.", username);

        // TODO: Refactor - folder on phone
        final File file = new File( PDF_RESOURCE_FOLDER_PATH + pdfReportName + ".pdf");
        IOUtils.copy(bis, new FileOutputStream(file));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @Valid @RequestBody final PainfulSyndrome newPainfulSyndrome) {
        final PainfulSyndrome oldPainfulSyndrome = illnessTypeService.findById(id);
        LOGGER.info("Successfully founded PainfulSyndrome with id: ´{}´.", id);

        final PainfulSyndrome updatedPainfulSyndrome = illnessTypeService.update(oldPainfulSyndrome, newPainfulSyndrome);
        LOGGER.info("Successfully updated PainfulSyndrome with id: ´{}´.", id);
        return new ResponseEntity<>(updatedPainfulSyndrome, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        final PainfulSyndrome searchedPainfulSyndrome = illnessTypeService.findById(id);
        LOGGER.info("Successfully founded PainfulSyndrome with id: ´{}´.", id);

        illnessTypeService.delete(searchedPainfulSyndrome);
        LOGGER.info("Successfully delete PainfulSyndrome with id: ´{}´.", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
