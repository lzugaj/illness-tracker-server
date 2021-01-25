package com.luv2code.illnesstracker.service.impl.pdf.helper;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.illness.type.Hyperthyroidism;
import com.luv2code.illnesstracker.service.FormatterService;
import com.luv2code.illnesstracker.service.PdfCellBuilderService;
import com.luv2code.illnesstracker.service.impl.formatter.FormatterServiceImpl;
import com.luv2code.illnesstracker.service.impl.pdf.PdfCellBuilderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class HyperthyroidismHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(HyperthyroidismHelper.class);

    public static void populate(final PdfPTable table, final User user) {
        Integer counter = 1;
        for (Hyperthyroidism hyperthyroidism : user.getHyperthyroid()) {
            final PdfCellBuilderService cellBuilderService = new PdfCellBuilderServiceImpl();

            PdfPCell hyperthyroidismCell = cellBuilderService.add(String.valueOf(counter));
            table.addCell(hyperthyroidismCell);

            hyperthyroidismCell = cellBuilderService.add(formatDateAndTime(hyperthyroidism.getDateOfPerformedMeasurement()));
            table.addCell(hyperthyroidismCell);

            hyperthyroidismCell = cellBuilderService.add(String.valueOf(hyperthyroidism.getFt3()));
            table.addCell(hyperthyroidismCell);

            hyperthyroidismCell = cellBuilderService.add(String.valueOf(hyperthyroidism.getFt4()));
            table.addCell(hyperthyroidismCell);

            hyperthyroidismCell = cellBuilderService.add(String.valueOf(hyperthyroidism.getTsh()));
            table.addCell(hyperthyroidismCell);

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
