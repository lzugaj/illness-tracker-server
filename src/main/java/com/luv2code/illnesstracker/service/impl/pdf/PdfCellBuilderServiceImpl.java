package com.luv2code.illnesstracker.service.impl.pdf;

import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.luv2code.illnesstracker.service.PdfCellBuilderService;

public class PdfCellBuilderServiceImpl implements PdfCellBuilderService {

    @Override
    public PdfPCell add(final String columnValue) {
        PdfPCell pdfCell = new PdfPCell(new Phrase(columnValue));
        pdfCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);

        return pdfCell;
    }
}
