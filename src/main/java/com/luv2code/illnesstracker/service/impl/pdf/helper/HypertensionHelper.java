package com.luv2code.illnesstracker.service.impl.pdf.helper;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.illness.type.Hypertension;
import com.luv2code.illnesstracker.domain.info.HypertensionInfo;
import com.luv2code.illnesstracker.service.FormatterService;
import com.luv2code.illnesstracker.service.IllnessTypeInfoService;
import com.luv2code.illnesstracker.service.PdfCellBuilderService;
import com.luv2code.illnesstracker.service.impl.formatter.FormatterServiceImpl;
import com.luv2code.illnesstracker.service.impl.pdf.PdfCellBuilderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

public class HypertensionHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(HypertensionHelper.class);

    public static void inform(final Document document, final IllnessTypeInfoService<HypertensionInfo> hypertensionInfoService) throws DocumentException {
        final List<HypertensionInfo> hypertensionInfos = hypertensionInfoService.findAll();
        final Font font = FontFactory.getFont(FontFactory.HELVETICA, 12);
        for (HypertensionInfo hypertensionInfo : hypertensionInfos) {
            final String hypertensionClassification = hypertensionInfo.getClassification();
            final String systolicRangeValue = hypertensionInfo.getSystolicRange();
            final String diastolicRangeValue = hypertensionInfo.getDiastolicRange();
            final Paragraph titleParagraph = new Paragraph(
                    hypertensionClassification + ": " + systolicRangeValue + " / " + diastolicRangeValue,
                    font);
            document.add(titleParagraph);
        }
    }

    public static void populate(final PdfPTable table, final User user) {
        Integer counter = 1;
        for (Hypertension hypertension : user.getHypertension()) {
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
