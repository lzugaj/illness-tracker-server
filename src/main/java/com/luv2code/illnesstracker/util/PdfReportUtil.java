package com.luv2code.illnesstracker.util;

import java.util.ArrayList;
import java.util.List;

public class PdfReportUtil {

    // Application name
    public static final String SYSTEM_NAME = "PIMS - User illness monitoring system";

    // Header and Footer
    public static final String CURRENT_DATE = "Date: ";
    public static final String CURRENT_PAGE = "Page ";
    public static final String RECTANGLE = "rectangle";

    // User
    public static final String USER_FULL_NAME = "Full name: ";
    public static final String USER_EMAIL = "Email: ";
    public static final String USER_DATE_OF_BIRTH = "Date of birth: ";
    public static final String USER_PHONE_NUMBER = "Phone number: ";

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

    // Hyperthyroidism
    public static final String HYPERTHYROIDISM_TITLE = "Hyperthyroidism values measurement history";
    public static final Integer HYPERTHYROIDISM_NUMBER_OF_COLUMNS = 5;

    public static final String HYPERTHYROIDISM_ID = "Id";
    public static final String HYPERTHYROIDISM_DATE_OF_MEASUREMENT = "Date of measurement";
    public static final String HYPERTHYROIDISM_TSH = "TSH";
    public static final String HYPERTHYROIDISM_FT3 = "FT3";
    public static final String HYPERTHYROIDISM_FT4 = "FT4";

    public static List<String> hyperthyroidismColumnNames;
    public static List<String> getHyperthyroidismColumnNames() {
        hyperthyroidismColumnNames = new ArrayList<>();
        hyperthyroidismColumnNames.add(PdfReportUtil.HYPERTHYROIDISM_ID);
        hyperthyroidismColumnNames.add(PdfReportUtil.HYPERTHYROIDISM_DATE_OF_MEASUREMENT);
        hyperthyroidismColumnNames.add(PdfReportUtil.HYPERTHYROIDISM_TSH);
        hyperthyroidismColumnNames.add(PdfReportUtil.HYPERTHYROIDISM_FT3);
        hyperthyroidismColumnNames.add(PdfReportUtil.HYPERTHYROIDISM_FT4);
        return hyperthyroidismColumnNames;
    }

    // Diabetes Mellitus Type II
    public static final String DIABETES_MELLITUS_TYPE_II_TITLE = "Diabetes Mellitus Type II values measurement history";
    public static final Integer DIABETES_MELLITUS_TYPE_II_NUMBER_OF_COLUMNS = 4;

    public static final String DIABETES_MELLITUS_TYPE_II_ID = "Id";
    public static final String DIABETES_MELLITUS_TYPE_II_DATE_OF_MEASUREMENT = "Date of measurement";
    public static final String DIABETES_MELLITUS_TYPE_II_GUK0 = "GUK0";
    public static final String DIABETES_MELLITUS_TYPE_II_GUK1 = "GUK1";

    public static List<String> diabetesMellitusTypeIIColumnNames;
    public static List<String> getDiabetesMellitusTypeIIColumnNames() {
        diabetesMellitusTypeIIColumnNames = new ArrayList<>();
        diabetesMellitusTypeIIColumnNames.add(PdfReportUtil.DIABETES_MELLITUS_TYPE_II_ID);
        diabetesMellitusTypeIIColumnNames.add(PdfReportUtil.DIABETES_MELLITUS_TYPE_II_DATE_OF_MEASUREMENT);
        diabetesMellitusTypeIIColumnNames.add(PdfReportUtil.DIABETES_MELLITUS_TYPE_II_GUK0);
        diabetesMellitusTypeIIColumnNames.add(PdfReportUtil.DIABETES_MELLITUS_TYPE_II_GUK1);
        return diabetesMellitusTypeIIColumnNames;
    }

    // Painful Syndromes
    public static final String PAINFUL_SYNDROMES_TITLE = "Painful Syndromes values measurement history";
    public static final Integer PAINFUL_SYNDROMES_NUMBER_OF_COLUMNS = 5;

    public static final String PAINFUL_SYNDROMES_ID = "Id";
    public static final String PAINFUL_SYNDROMES_DATE_OF_MEASUREMENT = "Date of measurement";
    public static final String PAINFUL_SYNDROMES_BODY_PART = "Body part";
    public static final String PAINFUL_SYNDROMES_DESCRIPTION = "Description";
    public static final String PAINFUL_SYNDROMES_VAS_VALUE = "VAS value";

    public static List<String> painfulSyndromesColumnNames;
    public static List<String> getPainfulSyndromesColumnNames() {
        diabetesMellitusTypeIIColumnNames = new ArrayList<>();
        diabetesMellitusTypeIIColumnNames.add(PdfReportUtil.PAINFUL_SYNDROMES_ID);
        diabetesMellitusTypeIIColumnNames.add(PdfReportUtil.PAINFUL_SYNDROMES_DATE_OF_MEASUREMENT);
        diabetesMellitusTypeIIColumnNames.add(PdfReportUtil.PAINFUL_SYNDROMES_BODY_PART);
        diabetesMellitusTypeIIColumnNames.add(PdfReportUtil.PAINFUL_SYNDROMES_DESCRIPTION);
        diabetesMellitusTypeIIColumnNames.add(PdfReportUtil.PAINFUL_SYNDROMES_VAS_VALUE);
        return painfulSyndromesColumnNames;
    }

    // Gastro Esophageal Reflux
    public static final String GASTRO_ESOPHAGEAL_REFLUX_TITLE = "Gastro Esophageal Reflux values measurement history";
    public static final Integer GASTRO_ESOPHAGEAL_REFLUX_NUMBER_OF_COLUMNS = 4;

    public static final String GASTRO_ESOPHAGEAL_REFLUX_ID = "Id";
    public static final String GASTRO_ESOPHAGEAL_REFLUX_DATE_OF_MEASUREMENT = "Date of measurement";
    public static final String GASTRO_ESOPHAGEAL_REFLUX_DATETIME_OF_LAST_MEAL = "Date and time of last meal";
    public static final String GASTRO_ESOPHAGEAL_REFLUX_DATETIME_OF_ONSET_OF_SYMPTOMS = "Date and time of onset of symptoms";

    public static List<String> gastroEsophagealRefluxColumnNames;
    public static List<String> getGastroEsophagealRefluxColumnNames() {
        diabetesMellitusTypeIIColumnNames = new ArrayList<>();
        diabetesMellitusTypeIIColumnNames.add(PdfReportUtil.GASTRO_ESOPHAGEAL_REFLUX_ID);
        diabetesMellitusTypeIIColumnNames.add(PdfReportUtil.GASTRO_ESOPHAGEAL_REFLUX_DATE_OF_MEASUREMENT);
        diabetesMellitusTypeIIColumnNames.add(PdfReportUtil.GASTRO_ESOPHAGEAL_REFLUX_DATETIME_OF_LAST_MEAL);
        diabetesMellitusTypeIIColumnNames.add(PdfReportUtil.GASTRO_ESOPHAGEAL_REFLUX_DATETIME_OF_ONSET_OF_SYMPTOMS);
        return gastroEsophagealRefluxColumnNames;
    }
}
