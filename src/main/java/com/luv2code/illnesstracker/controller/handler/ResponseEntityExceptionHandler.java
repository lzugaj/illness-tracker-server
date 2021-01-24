package com.luv2code.illnesstracker.controller.handler;

import com.luv2code.illnesstracker.controller.handler.response.ApiResponse;
import com.luv2code.illnesstracker.exception.UsernameAlreadyExistsException;
import com.luv2code.illnesstracker.exception.EntityNotFoundException;
import com.luv2code.illnesstracker.exception.IllnessOptionIsNotSelectedException;
import com.luv2code.illnesstracker.exception.PdfGenerateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse> handleGlobalRequestException(final Exception exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        return createApiResponseMessage(internalServerError, exception, httpServletRequest);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ApiResponse> handleEntityNotFoundRequestException(final EntityNotFoundException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        return createApiResponseMessage(notFound, exception, httpServletRequest);
    }

    @ExceptionHandler(value = UsernameAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleEmailAlreadyExistsRequestException(final UsernameAlreadyExistsException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus alreadyExists = HttpStatus.CONFLICT;
        return createApiResponseMessage(alreadyExists, exception, httpServletRequest);
    }

    @ExceptionHandler(value = IllnessOptionIsNotSelectedException.class)
    public ResponseEntity<ApiResponse> handleIllnessOptionIsNotSelectedRequestException(final IllnessOptionIsNotSelectedException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus notAcceptable = HttpStatus.NOT_ACCEPTABLE;
        return createApiResponseMessage(notAcceptable, exception, httpServletRequest);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidRequestException(final MethodArgumentNotValidException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return createApiResponseMessage(badRequest, exception, httpServletRequest);
    }

    @ExceptionHandler(value = PdfGenerateException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidRequestException(final PdfGenerateException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return createApiResponseMessage(badRequest, exception, httpServletRequest);
    }

    private ResponseEntity<ApiResponse> createApiResponseMessage(final HttpStatus httpStatus, final Exception exception, final HttpServletRequest httpServletRequest) {
        final ApiResponse apiResponse = ApiResponse.builder()
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .httpStatusCode(httpStatus.value())
                .httpStatus(httpStatus)
                .message(exception.getMessage())
                .path(httpServletRequest.getRequestURI())
                .build();

        return new ResponseEntity<>(apiResponse, httpStatus);
    }
}
