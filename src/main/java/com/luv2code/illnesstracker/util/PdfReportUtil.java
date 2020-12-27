package com.luv2code.illnesstracker.util;

import java.util.ArrayList;
import java.util.List;

public class PdfReportUtil {

    // Application name
    public static final String SYSTEM_NAME = "PIMS - Patient illness monitoring system";

    // Header and Footer
    public static final String CURRENT_DATE = "Date: ";
    public static final String CURRENT_PAGE = "Page ";
    public static final String RECTANGLE = "rectangle";

    // Patient
    public static final String PATIENT_FULL_NAME = "Full name: ";
    public static final String PATIENT_OIB = "Oib: ";
    public static final String PATIENT_EMAIL = "Email: ";
    public static final String PATIENT_PHONE_NUMBER = "Phone number: ";
    public static final String PATIENT_DATE_OF_BIRTH = "Date of birth: ";

    // Body Mass Index
    public static final String BODY_MASS_INDEX_TITLE = "Body mass index measurement history";
    public static final Integer BMI_NUMBER_OF_COLUMNS = 6;

    public static final String BMI_ID = "Id";
    public static final String BMI_DATE_OF_MEASUREMENT = "Date of measurement";
    public static final String BMI_HEIGHT = "Height";
    public static final String BMI_WEIGHT = "Weight";
    public static final String BMI_INDEX_VALUE = "Index value";
    public static final String BMI_CLASSIFICATION = "Classification";
    public static final String BMI_CM = " cm";
    public static final String BMI_KG = " kg";

    public static List<String> bmiColumnNames;
    public static List<String> getBMIColumnNames() {
        bmiColumnNames = new ArrayList<>();
        bmiColumnNames.add(PdfReportUtil.BMI_ID);
        bmiColumnNames.add(PdfReportUtil.BMI_DATE_OF_MEASUREMENT);
        bmiColumnNames.add(PdfReportUtil.BMI_HEIGHT);
        bmiColumnNames.add(PdfReportUtil.BMI_WEIGHT);
        bmiColumnNames.add(PdfReportUtil.BMI_INDEX_VALUE);
        bmiColumnNames.add(PdfReportUtil.BMI_CLASSIFICATION);
        return bmiColumnNames;
    }

    // Hypertension
    public static final String HYPERTENSION_TITLE = "Hypertension values measurement history";
    public static final Integer HYPERTENSION_NUMBER_OF_COLUMNS = 5;

    public static final String HYPERTENSION_ID = "Id";
    public static final String HYPERTENSION_DATE_OF_MEASUREMENT = "Date of measurement";
    public static final String HYPERTENSION_SYSTOLIC = "Systolic";
    public static final String HYPERTENSION_DIASTOLIC = "Diastolic";
    public static final String HYPERTENSION_CLASSIFICATION = "Classification";

    public static List<String> hypertensionColumnNames;
    public static List<String> getHypertensionColumnNames() {
        hypertensionColumnNames = new ArrayList<>();
        hypertensionColumnNames.add(PdfReportUtil.HYPERTENSION_ID);
        hypertensionColumnNames.add(PdfReportUtil.HYPERTENSION_DATE_OF_MEASUREMENT);
        hypertensionColumnNames.add(PdfReportUtil.HYPERTENSION_SYSTOLIC);
        hypertensionColumnNames.add(PdfReportUtil.HYPERTENSION_DIASTOLIC);
        hypertensionColumnNames.add(PdfReportUtil.HYPERTENSION_CLASSIFICATION);
        return hypertensionColumnNames;
    }
}
