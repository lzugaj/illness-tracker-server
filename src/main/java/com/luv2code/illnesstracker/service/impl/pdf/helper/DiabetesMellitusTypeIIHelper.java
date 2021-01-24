package com.luv2code.illnesstracker.service.impl.pdf.helper;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.illness.type.DiabetesMellitusTypeII;
import com.luv2code.illnesstracker.service.FormatterService;
import com.luv2code.illnesstracker.service.PdfCellBuilderService;
import com.luv2code.illnesstracker.service.impl.formatter.FormatterServiceImpl;
import com.luv2code.illnesstracker.service.impl.pdf.PdfCellBuilderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class DiabetesMellitusTypeIIHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiabetesMellitusTypeIIHelper.class);

    public static void populate(final PdfPTable table, final Patient patient) {
        Integer counter = 1;
        for (DiabetesMellitusTypeII diabetesMellitusTypeII : patient.getDiabetesMellitusTypesII()) {
            final PdfCellBuilderService cellBuilderService = new PdfCellBuilderServiceImpl();

            PdfPCell diabetesMellitusTypeIICell = cellBuilderService.add(String.valueOf(counter));
            table.addCell(diabetesMellitusTypeIICell);

            diabetesMellitusTypeIICell = cellBuilderService.add(formatDateAndTime(diabetesMellitusTypeII.getDateOfPerformedMeasurement()));
            table.addCell(diabetesMellitusTypeIICell);

            diabetesMellitusTypeIICell = cellBuilderService.add(String.valueOf(diabetesMellitusTypeII.getGuk0()));
            table.addCell(diabetesMellitusTypeIICell);

            diabetesMellitusTypeIICell = cellBuilderService.add(String.valueOf(diabetesMellitusTypeII.getGuk1()));
            table.addCell(diabetesMellitusTypeIICell);

            counter++;
        }
    }

    private static String formatDateAndTime(final LocalDateTime dateOfPerformedMeasurement) {
        final FormatterService formatterService = new FormatterServiceImpl();
        final String dateFormatter = formatterService.formatDate(dateOfPerformedMeasurement);
        final String timeFormatter = formatterService.formatTime(dateOfPerformedMeasurement);
        LOGGER.info("Successfully formatted date and time with value: ´{} {}´.",
                dateFormatter, timeFormatter);

        return dateFormatter + " " + timeFormatter;
    }
}
