package com.luv2code.illnesstracker.exception;

public class PdfGenerateException extends AbstractEntityException {

    private static final long serialVersionUID = 1L;

    public PdfGenerateException(final String entityName,
                                               final String fieldName,
                                               final String fieldValue) {
        this(entityName, fieldName, fieldValue, null);
    }

    public PdfGenerateException(final String entityName,
                                               final String fieldName,
                                               final String fieldValue,
                                               final Throwable cause) {
        super(entityName, fieldName, fieldValue, createMessage(entityName, fieldName, fieldValue), cause);
    }

    private static String createMessage(final String entityName, final String fieldName, final String fieldValue) {
        return String.format("Pdf for entity '%s' with '%s' value '%s' is not generated.",
                entityName, fieldName, fieldValue);
    }

}
