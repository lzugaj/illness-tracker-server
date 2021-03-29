package com.luv2code.illnesstracker.service.impl.pdf.helper;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.illness.type.GastroEsophagealReflux;
import com.luv2code.illnesstracker.service.FormatterService;
import com.luv2code.illnesstracker.service.PdfCellBuilderService;
import com.luv2code.illnesstracker.service.impl.formatter.FormatterServiceImpl;
import com.luv2code.illnesstracker.service.impl.pdf.PdfCellBuilderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class GastroEsophagealRefluxHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(GastroEsophagealRefluxHelper.class);

    public static void populate(final PdfPTable table, final User user) {
        Integer counter = 1;
        for (GastroEsophagealReflux gastroEsophagealReflux : user.getGastroEsophagealRefluxes()) {
            final PdfCellBuilderService cellBuilderService = new PdfCellBuilderServiceImpl();

            PdfPCell gerCell = cellBuilderService.add(String.valueOf(counter));
            table.addCell(gerCell);

            gerCell = cellBuilderService.add(formatDateAndTime(gastroEsophagealReflux.getDateOfPerformedMeasurement()));
            table.addCell(gerCell);

            gerCell = cellBuilderService.add(String.valueOf(gastroEsophagealReflux.getDatetimeOfLastMeal()));
            table.addCell(gerCell);

            gerCell = cellBuilderService.add(String.valueOf(gastroEsophagealReflux.getDatetimeOfOnsetOfSymptoms()));
            table.addCell(gerCell);

            gerCell = cellBuilderService.add(String.valueOf(gastroEsophagealReflux.getDescription()));
            table.addCell(gerCell);

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
