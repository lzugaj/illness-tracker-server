package com.luv2code.illnesstracker.service.impl.pdf.helper;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.luv2code.illnesstracker.util.PdfReportUtil;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class HeaderAndFooterEventHelper extends PdfPageEventHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeaderAndFooterEventHelper.class);

    @SneakyThrows
    public void onStartPage(final PdfWriter pdfWriter, final Document document) {
        final Rectangle rect = pdfWriter.getBoxSize(PdfReportUtil.RECTANGLE);
        rect.enableBorderSide(Rectangle.TOP);
        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(1);
        document.add(rect);

        ColumnText.showTextAligned(
                pdfWriter.getDirectContent(),
                Element.ALIGN_CENTER,
                new Phrase(
                        PdfReportUtil.SYSTEM_NAME,
                        FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC)
                ),
                rect.getRight() / 2,
                rect.getTop() + 10,
                0);
    }

    @SneakyThrows
    public void onEndPage(final PdfWriter pdfWriter, final Document document) {
        final Rectangle rect = pdfWriter.getBoxSize(PdfReportUtil.RECTANGLE);
        rect.enableBorderSide(Rectangle.BOTTOM);
        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(1);
        document.add(rect);

        ColumnText.showTextAligned(
                pdfWriter.getDirectContent(),
                Element.ALIGN_CENTER,
                new Phrase(
                        getCurrentDate(),
                        FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC)
                ),
                rect.getLeft() + 70,
                rect.getBottom() - 20,
                0);

        ColumnText.showTextAligned(
                pdfWriter.getDirectContent(),
                Element.ALIGN_CENTER,
                new Phrase(
                        getPageNumberFormatter(pdfWriter),
                        FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC)
                ),
                rect.getRight() - 50,
                rect.getBottom() - 20,
                0);
    }

    private static String getCurrentDate() {
        final LocalDateTime currentDateTime = LocalDateTime.now();
        final int dayOfMonth = currentDateTime.getDayOfMonth();
        final int monthValue = currentDateTime.getMonthValue();
        final int year = currentDateTime.getYear();
        final String currentDateString = dayOfMonth + "." + monthValue + "." + year;
        LOGGER.info("Current date is: ´{}´.", currentDateString);

        return PdfReportUtil.CURRENT_DATE + currentDateString;
    }

    private static String getPageNumberFormatter(final PdfWriter pdfWriter) {
        final int currentPageNumber = pdfWriter.getCurrentPageNumber();
        LOGGER.info("Current page number is: ´{}´.", currentPageNumber);

        return PdfReportUtil.CURRENT_PAGE + currentPageNumber;
    }
}
