package com.luv2code.illnesstracker.service.impl.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.exception.PdfGenerateException;
import com.luv2code.illnesstracker.service.PdfFactoryService;
import com.luv2code.illnesstracker.service.impl.pdf.helper.*;
import com.luv2code.illnesstracker.util.IllnessTypeUtil;
import com.luv2code.illnesstracker.util.PdfReportUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfFactoryServiceImpl implements PdfFactoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PdfFactoryServiceImpl.class);

    @Override
    public ByteArrayInputStream generate(final Patient patient, final String illnessName) {
        final Document document = new Document();
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            final PdfWriter writer = PdfWriter.getInstance(document, stream);
            final Rectangle rectangle = new Rectangle(0, 30, 600, 810);
            writer.setBoxSize(PdfReportUtil.RECTANGLE, rectangle);

            final HeaderAndFooterEventHelper headerAndFooter = new HeaderAndFooterEventHelper();
            writer.setPageEvent(headerAndFooter);
            document.open();

            setupTitle(document, illnessName);
            setupPatientInfo(document, patient);

            final PdfPTable table;
            switch (illnessName) {
                case IllnessTypeUtil.BODY_MASS_INDEX:
                    table = new PdfPTable(PdfReportUtil.BMI_NUMBER_OF_COLUMNS);
                    table.setWidthPercentage(95);
                    table.setWidths(new int[]{1, 6, 3, 3, 4, 6});
                    createTable(document, table, patient, illnessName, PdfReportUtil.getBMIColumnNames());
                    break;
                case IllnessTypeUtil.HYPERTENSION:
                    table = new PdfPTable(PdfReportUtil.HYPERTENSION_NUMBER_OF_COLUMNS);
                    table.setWidthPercentage(95);
                    table.setWidths(new int[]{1, 6, 3, 3, 7});
                    createTable(document, table, patient, illnessName, PdfReportUtil.getHypertensionColumnNames());
                    break;
                case IllnessTypeUtil.HYPERTHYROIDISM:
                    table = new PdfPTable(PdfReportUtil.HYPERTHYROIDISM_NUMBER_OF_COLUMNS);
                    table.setWidthPercentage(95);
                    table.setWidths(new int[]{1, 6, 3, 3, 3});
                    createTable(document, table, patient, illnessName, PdfReportUtil.getHyperthyroidismColumnNames());
                    break;
                case IllnessTypeUtil.DIABETES_MELLITUS_TYPE_II:
                    table = new PdfPTable(PdfReportUtil.DIABETES_MELLITUS_TYPE_II_NUMBER_OF_COLUMNS);
                    table.setWidthPercentage(95);
                    table.setWidths(new int[]{1, 6, 3, 3});
                    createTable(document, table, patient, illnessName, PdfReportUtil.getDiabetesMellitusTypeIIColumnNames());
                    break;
                case IllnessTypeUtil.PAINFUL_SYNDROMES:
                    table = new PdfPTable(PdfReportUtil.PAINFUL_SYNDROMES_NUMBER_OF_COLUMNS);
                    table.setWidthPercentage(95);
                    table.setWidths(new int[]{1, 6, 6, 6, 3});
                    createTable(document, table, patient, illnessName, PdfReportUtil.getPainfulSyndromesColumnNames());
                    break;
                case IllnessTypeUtil.GASTRO_ESOPHAGEAL_REFLUX:
                    table = new PdfPTable(PdfReportUtil.GASTRO_ESOPHAGEAL_REFLUX_NUMBER_OF_COLUMNS);
                    table.setWidthPercentage(95);
                    table.setWidths(new int[]{1, 6, 6, 6});
                    createTable(document, table, patient, illnessName, PdfReportUtil.getGastroEsophagealRefluxColumnNames());
                    break;
            }

            document.close();
        } catch (final DocumentException e) {
            LOGGER.error("Error while generating pdf report for bmi.");
            LOGGER.error(e.getMessage());
            throw new PdfGenerateException("Patient", "id", String.valueOf(patient.getId()));
        }

        return new ByteArrayInputStream(stream.toByteArray());
    }

    private void createTable(final Document document, final PdfPTable table, final Patient patient, final String illnessName, final List<String> columnNames) throws DocumentException {
        for (String columnName : columnNames) {
            final Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            final PdfPCell cell = new PdfPCell(new Phrase(columnName, headFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        switch (illnessName) {
            case IllnessTypeUtil.BODY_MASS_INDEX:
                BodyMassIndexHelper.populate(table, patient);
                break;
            case IllnessTypeUtil.HYPERTENSION:
                HypertensionHelper.populate(table, patient);
                break;
            case IllnessTypeUtil.HYPERTHYROIDISM:
                HyperthyroidismHelper.populate(table, patient);
                break;
            case IllnessTypeUtil.DIABETES_MELLITUS_TYPE_II:
                DiabetesMellitusTypeIIHelper.populate(table, patient);
                break;
            case IllnessTypeUtil.PAINFUL_SYNDROMES:
                PatientSyndromeHelper.populate(table, patient);
                break;
            case IllnessTypeUtil.GASTRO_ESOPHAGEAL_REFLUX:
                GastroEsophagealRefluxHelper.populate(table, patient);
                break;
        }

        document.add(table);
    }

    private static void setupTitle(final Document document, final String illnessName) throws DocumentException {
        final Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Font.UNDERLINE|Font.ITALIC);
        final String pdfTitle = getPdfTitle(illnessName);
        final Paragraph titleParagraph = new Paragraph(pdfTitle, headFont);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(titleParagraph);

        document.add(Chunk.NEWLINE);
    }

    private static String getPdfTitle(final String illnessName) {
        String title = "";
        switch (illnessName) {
            case IllnessTypeUtil.BODY_MASS_INDEX:
                title = PdfReportUtil.BODY_MASS_INDEX_TITLE;
                break;
            case IllnessTypeUtil.HYPERTENSION:
                title = PdfReportUtil.HYPERTENSION_TITLE;
                break;
            case IllnessTypeUtil.HYPERTHYROIDISM:
                title = PdfReportUtil.HYPERTHYROIDISM_TITLE;
                break;
            case IllnessTypeUtil.DIABETES_MELLITUS_TYPE_II:
                title = PdfReportUtil.DIABETES_MELLITUS_TYPE_II_TITLE;
                break;
            case IllnessTypeUtil.PAINFUL_SYNDROMES:
                title = PdfReportUtil.PAINFUL_SYNDROMES_TITLE;
                break;
            case IllnessTypeUtil.GASTRO_ESOPHAGEAL_REFLUX:
                title = PdfReportUtil.GASTRO_ESOPHAGEAL_REFLUX_TITLE;
                break;
        }

        return title;
    }

    private static void setupPatientInfo(final Document document, final Patient patient) throws DocumentException {
        final Font dynamicTextFont = FontFactory.getFont(FontFactory.HELVETICA, 14);
        final Paragraph fullName = new Paragraph(PdfReportUtil.PATIENT_FULL_NAME + getPatientFullName(patient), dynamicTextFont);
        final Paragraph dateOfBirth = new Paragraph(PdfReportUtil.PATIENT_DATE_OF_BIRTH + getPatientDateOfBirth(patient), dynamicTextFont);
        final Paragraph email = new Paragraph(PdfReportUtil.PATIENT_EMAIL + patient.getEmail(), dynamicTextFont);
        final Paragraph phoneNumber = new Paragraph(PdfReportUtil.PATIENT_PHONE_NUMBER + patient.getPhoneNumber(), dynamicTextFont);

        document.add(fullName);
        document.add(dateOfBirth);
        document.add(email);
        document.add(phoneNumber);

        document.add(Chunk.NEWLINE);
    }

    private static String getPatientFullName(final Patient patient) {
        return patient.getFirstName() + " " + patient.getLastName();
    }

    private static String getPatientDateOfBirth(final Patient patient) {
        return patient.getDateOfBirth().getDayOfMonth() + "." +
                patient.getDateOfBirth().getMonthValue() + "." +
                patient.getDateOfBirth().getYear();
    }
}
