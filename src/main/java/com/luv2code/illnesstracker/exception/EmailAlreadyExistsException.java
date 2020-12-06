package com.luv2code.illnesstracker.exception;

public class EmailAlreadyExistsException extends AbstractEntityException {

    private static final long serialVersionUID = 1L;

    public EmailAlreadyExistsException(final String entityName,
                                       final String fieldName,
                                       final String fieldValue) {
        this(entityName, fieldName, fieldValue, null);
    }

    public EmailAlreadyExistsException(final String entityName,
                                       final String fieldName,
                                       final String fieldValue,
                                       final Throwable cause) {
        super(entityName, fieldName, fieldValue, createMessage(entityName, fieldName, fieldValue), cause);
    }

    private static String createMessage(final String entityName, final String fieldName, final String fieldValue) {
        return String.format("Entity '%s' with '%s' value '%s' already exists.",
                entityName, fieldName, fieldValue);
    }
}
