package com.luv2code.illnesstracker.service;

import com.itextpdf.text.pdf.PdfPCell;

public interface PdfCellBuilderService {

    PdfPCell add(final String columnValue);

}
