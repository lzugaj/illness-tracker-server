package com.luv2code.illnesstracker.service.impl.pdf.helper;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.illness.BodyMassIndex;
import com.luv2code.illnesstracker.service.FormatterService;
import com.luv2code.illnesstracker.service.PdfCellBuilderService;
import com.luv2code.illnesstracker.service.impl.formatter.FormatterServiceImpl;
import com.luv2code.illnesstracker.service.impl.pdf.PdfCellBuilderServiceImpl;
import com.luv2code.illnesstracker.util.PdfReportUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class BodyMassIndexHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(BodyMassIndexHelper.class);

    public static void populate(final PdfPTable table, final Patient patient) {
        Integer counter = 1;
        for (BodyMassIndex bodyMassIndex : patient.getBodyMassIndexes()) {
            final PdfCellBuilderService cellBuilderService = new PdfCellBuilderServiceImpl();

            PdfPCell bmiCell = cellBuilderService.add(String.valueOf(counter));
            table.addCell(bmiCell);

            bmiCell = cellBuilderService.add(formatDateAndTime(bodyMassIndex.getDateOfPerformedMeasurement()));
            table.addCell(bmiCell);

            bmiCell = cellBuilderService.add(bodyMassIndex.getHeight() + PdfReportUtil.BMI_CM);
            table.addCell(bmiCell);

            bmiCell = cellBuilderService.add(bodyMassIndex.getWeight() + PdfReportUtil.BMI_KG);
            table.addCell(bmiCell);

            bmiCell = cellBuilderService.add(String.valueOf(bodyMassIndex.getIndexValue()));
            table.addCell(bmiCell);

            bmiCell = cellBuilderService.add(String.valueOf(bodyMassIndex.getBodyMassIndexInfo().getClassification()));
            table.addCell(bmiCell);

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
