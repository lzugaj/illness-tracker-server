package com.luv2code.illnesstracker.service.impl.pdf;

import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.repository.DummyRepository;
import com.luv2code.illnesstracker.service.PdfNamingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Locale;

@Service
public class PdfNamingServiceImpl implements PdfNamingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PdfNamingServiceImpl.class);

    private final DummyRepository dummyRepository;

    @Autowired
    public PdfNamingServiceImpl(final DummyRepository dummyRepository) {
        this.dummyRepository = dummyRepository;
    }

    @Override
    public String generate(final Patient patient, final String illnessType) {
        final String patientFullName = getPatientFullName(patient);
        final String currentDate = getCurrentDate();
        final Long id = dummyRepository.getNextSeriesId();

        LOGGER.info("Generating pdf report for Patient with id: ´{}´.", patient.getId());
        return String.format("%s%05d_%s%s",
                currentDate, id, patientFullName, illnessType);
    }

    private String getPatientFullName(final Patient patient) {
        final String firstName = getPatientFirstName(patient);
        final String lastName = getPatientLastName(patient);
        return String.format("%s%s", firstName, lastName);
    }

    private String getPatientFirstName(final Patient patient) {
        StringBuilder firstNames = new StringBuilder();
        final String[] names = patient.getFirstName().toLowerCase(Locale.ROOT).split(" ");
        for (String name : names) {
            firstNames.append(name).append("_");
        }

        LOGGER.info("Patient first names are: ´{}´.", firstNames);
        return String.valueOf(firstNames);
    }

    private String getPatientLastName(final Patient patient) {
        StringBuilder lastNames = new StringBuilder();
        final String[] surnames = patient.getLastName().toLowerCase(Locale.ROOT).split(" ");
        for (String surname : surnames) {
            lastNames.append(surname).append("_");
        }

        LOGGER.info("Patient last names are: ´{}´.", lastNames);
        return String.valueOf(lastNames);
    }

    private String getCurrentDate() {
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        if (Integer.parseInt(month) < 10) {
            month = "0" + Calendar.getInstance().get(Calendar.MONTH);
        }

        String date = String.valueOf(Calendar.getInstance().get(Calendar.DATE));
        if (Integer.parseInt(date) < 10) {
            date = "0" + Calendar.getInstance().get(Calendar.DATE);
        }

        LOGGER.info("Current date for generated pdf file is: ´{}.{}.{}´.", date, month, year);
        return String.format("%s%s%s", year, month, date);
    }
}
