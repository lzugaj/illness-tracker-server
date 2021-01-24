package com.luv2code.illnesstracker.service.impl.pdf.helper;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.illness.type.Hypertension;
import com.luv2code.illnesstracker.service.FormatterService;
import com.luv2code.illnesstracker.service.PdfCellBuilderService;
import com.luv2code.illnesstracker.service.impl.formatter.FormatterServiceImpl;
import com.luv2code.illnesstracker.service.impl.pdf.PdfCellBuilderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class HypertensionHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(HypertensionHelper.class);

    public static void populate(final PdfPTable table, final Patient patient) {
        Integer counter = 1;
        for (Hypertension hypertension : patient.getHypertension()) {
            final PdfCellBuilderService cellBuilderService = new PdfCellBuilderServiceImpl();

            PdfPCell hypertensionCell = cellBuilderService.add(String.valueOf(counter));
            table.addCell(hypertensionCell);

            hypertensionCell = cellBuilderService.add(formatDateAndTime(hypertension.getDateOfPerformedMeasurement()));
            table.addCell(hypertensionCell);

            hypertensionCell = cellBuilderService.add(String.valueOf(hypertension.getSystolic()));
            table.addCell(hypertensionCell);

            hypertensionCell = cellBuilderService.add(String.valueOf(hypertension.getDiastolic()));
            table.addCell(hypertensionCell);

            hypertensionCell = cellBuilderService.add(String.valueOf(hypertension.getHypertensionInfo().getClassification()));
            table.addCell(hypertensionCell);

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
