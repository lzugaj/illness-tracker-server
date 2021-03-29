package com.luv2code.illnesstracker.controller;

import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.illness.type.BodyMassIndex;
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
@RequestMapping("/body-mass-indexes")
public class BodyMassIndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BodyMassIndexController.class);

    private static final String BMI = "bmi";
    private static final String PDF_RESOURCE_FOLDER_PATH = "C:\\Workspace\\illness-tracker\\illness-tracker-server\\src\\main\\resources\\pdf\\bmi";

    private final IllnessTypeService<BodyMassIndex> illnessTypeService;

    private final UserService userService;

    private final PdfNamingService pdfNamingService;

    private final PdfFactoryService pdfFactoryService;

    @Autowired
    public BodyMassIndexController(@Qualifier("bodyMassIndexServiceImpl") final IllnessTypeService<BodyMassIndex> illnessTypeService,
                                   final UserService userService,
                                   final PdfNamingService pdfNamingService,
                                   final PdfFactoryService pdfFactoryService) {
        this.illnessTypeService = illnessTypeService;
        this.userService = userService;
        this.pdfNamingService = pdfNamingService;
        this.pdfFactoryService = pdfFactoryService;
    }

    @PostMapping("/user/{username}")
    public ResponseEntity<?> save(@PathVariable final String username, @Valid @RequestBody final BodyMassIndex bodyMassIndex) {
        final User user = userService.findByUsername(username);
        LOGGER.info("Successfully founded User with username: ´{}´.", username);

        final BodyMassIndex newBodyMassIndex = illnessTypeService.save(user, bodyMassIndex);
        LOGGER.info("Successfully save new BodyMassIndex with id: ´{}´.", bodyMassIndex.getId());
        return new ResponseEntity<>(newBodyMassIndex, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable final Long id) {
        final BodyMassIndex searchedBodyMassIndex = illnessTypeService.findById(id);
        LOGGER.info("Successfully founded BodyMassIndex with id: ´{}´.", id);
        return new ResponseEntity<>(searchedBodyMassIndex, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        final List<BodyMassIndex> bodyMassIndexes = illnessTypeService.findAll();
        LOGGER.info("Successfully founded all BodyMassIndex.");
        return new ResponseEntity<>(bodyMassIndexes, HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> findAllForUser(@PathVariable final String username) {
        final User user = userService.findByUsername(username);
        LOGGER.info("Successfully founded User with username: ´{}´.", username);

        final List<BodyMassIndex> bodyMassIndexes = illnessTypeService.findAllForUser(user);
        LOGGER.info("Successfully founded all BodyMassIndex for User with username: ´{}´.", username);
        return new ResponseEntity<>(bodyMassIndexes, HttpStatus.OK);
    }

    @GetMapping("/user/{username}/report")
    public ResponseEntity<?> generateReport(@PathVariable final String username) throws IOException {
        final User user = userService.findByUsername(username);
        LOGGER.info("Successfully founded User with username: ´{}´.", username);

        final String pdfReportName = pdfNamingService.generate(user, BMI);
        LOGGER.info("Successfully generated BodyMassIndex pdf name: ´{}´.", pdfReportName);

        final ByteArrayInputStream bis = pdfFactoryService.generate(user, IllnessTypeUtil.BODY_MASS_INDEX);
        LOGGER.info("Successfully generated BodyMassIndex pdf file for User with username: ´{}´.", username);

        // TODO: Refactor - folder on phone
        final File file = new File( PDF_RESOURCE_FOLDER_PATH + pdfReportName + ".pdf");
        IOUtils.copy(bis, new FileOutputStream(file));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @Valid @RequestBody final BodyMassIndex newBodyMassIndex) {
        final BodyMassIndex oldBodyMassIndex = illnessTypeService.findById(id);
        LOGGER.info("Successfully founded BodyMassIndex with id: ´{}´.", id);

        final BodyMassIndex updatedBodyMassIndex = illnessTypeService.update(oldBodyMassIndex, newBodyMassIndex);
        LOGGER.info("Successfully updated BodyMassIndex with id: ´{}´.", id);
        return new ResponseEntity<>(updatedBodyMassIndex, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        final BodyMassIndex searchedBodyMassIndex = illnessTypeService.findById(id);
        LOGGER.info("Successfully founded BodyMassIndex with id: ´{}´.", id);

        illnessTypeService.delete(searchedBodyMassIndex);
        LOGGER.info("Successfully delete BodyMassIndex with id: ´{}´.", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
