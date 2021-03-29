package com.luv2code.illnesstracker.controller;

import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.illness.type.Hypertension;
import com.luv2code.illnesstracker.service.*;
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
@RequestMapping("/hypertensive")
public class HypertensionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HypertensionController.class);

    private static final String HYPERTENSION = "hypertension";
    private static final String PDF_RESOURCE_FOLDER_PATH = "C:\\Workspace\\illness-tracker\\illness-tracker-server\\src\\main\\resources\\pdf\\";

    private final IllnessTypeService<Hypertension> illnessTypeService;

    private final UserService userService;

    private final PdfNamingService pdfNamingService;

    private final PdfFactoryService pdfFactoryService;

    @Autowired
    public HypertensionController(@Qualifier("hypertensionServiceImpl") final IllnessTypeService<Hypertension> illnessTypeService,
                                  final UserService userService,
                                  final PdfNamingService pdfNamingService,
                                  final PdfFactoryService pdfFactoryService) {
        this.illnessTypeService = illnessTypeService;
        this.userService = userService;
        this.pdfNamingService = pdfNamingService;
        this.pdfFactoryService = pdfFactoryService;
    }

    @PostMapping("/user/{username}")
    public ResponseEntity<?> save(@PathVariable final String username, @Valid @RequestBody final Hypertension hypertension) {
        final User user = userService.findByUsername(username);
        LOGGER.info("Successfully founded User with username: ´{}´.", username);

        final Hypertension newHypertension = illnessTypeService.save(user, hypertension);
        LOGGER.info("Successfully save new Hypertension with id: ´{}´.", hypertension.getId());
        return new ResponseEntity<>(newHypertension, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable final Long id) {
        final Hypertension searchedHypertension = illnessTypeService.findById(id);
        LOGGER.info("Successfully founded Hypertension with id: ´{}´.", id);
        return new ResponseEntity<>(searchedHypertension, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        final List<Hypertension> hypertensive = illnessTypeService.findAll();
        LOGGER.info("Successfully founded all Hypertension.");
        return new ResponseEntity<>(hypertensive, HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> findAllForUser(@PathVariable final String username) {
        final User user = userService.findByUsername(username);
        LOGGER.info("Successfully founded User with username: ´{}´.", username);

        final List<Hypertension> hypertensive = illnessTypeService.findAllForUser(user);
        LOGGER.info("Successfully founded all Hypertension for User with username: ´{}´.", username);
        return new ResponseEntity<>(hypertensive, HttpStatus.OK);
    }

    @GetMapping("/user/{username}/report")
    public ResponseEntity<?> generateReport(@PathVariable final String username) throws IOException {
        final User user = userService.findByUsername(username);
        LOGGER.info("Successfully founded User with username: ´{}´.", username);

        final String pdfReportName = pdfNamingService.generate(user, HYPERTENSION);
        LOGGER.info("Successfully generated Hypertension pdf name: ´{}´.", pdfReportName);

        final ByteArrayInputStream bis = pdfFactoryService.generate(user, IllnessTypeUtil.HYPERTENSION);
        LOGGER.info("Successfully generated Hypertension pdf file for User with username: ´{}´.", username);

        // TODO: Refactor
        final File file = new File( PDF_RESOURCE_FOLDER_PATH + pdfReportName + ".pdf");
        IOUtils.copy(bis, new FileOutputStream(file));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @Valid @RequestBody final Hypertension newHypertension) {
        final Hypertension oldHypertension = illnessTypeService.findById(id);
        LOGGER.info("Successfully founded Hypertension with id: ´{}´.", id);

        final Hypertension updatedHypertension = illnessTypeService.update(oldHypertension, newHypertension);
        LOGGER.info("Successfully updated Hypertension with id: ´{}´.", id);
        return new ResponseEntity<>(updatedHypertension, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        final Hypertension searchedHypertension = illnessTypeService.findById(id);
        LOGGER.info("Successfully founded Hypertension with id: ´{}´.", id);

        illnessTypeService.delete(searchedHypertension);
        LOGGER.info("Successfully delete Hypertension with id: ´{}´.", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
