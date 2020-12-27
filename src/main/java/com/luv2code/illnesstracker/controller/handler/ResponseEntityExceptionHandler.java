package com.luv2code.illnesstracker.controller.handler;

import com.luv2code.illnesstracker.controller.handler.response.ApiResponse;
import com.luv2code.illnesstracker.exception.EmailAlreadyExistsException;
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
        final ApiResponse apiResponse = ApiResponse.builder()
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .httpStatusCode(internalServerError.value())
                .httpStatus(internalServerError)
                .message(exception.getMessage())
                .path(httpServletRequest.getRequestURI())
                .build();

        return new ResponseEntity<>(apiResponse, internalServerError);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ApiResponse> handleEntityNotFoundRequestException(final EntityNotFoundException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        final ApiResponse apiResponse = ApiResponse.builder()
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .httpStatusCode(notFound.value())
                .httpStatus(notFound)
                .message(exception.getMessage())
                .path(httpServletRequest.getRequestURI())
                .build();

        return new ResponseEntity<>(apiResponse, notFound);
    }

    @ExceptionHandler(value = EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleEmailAlreadyExistsRequestException(final EmailAlreadyExistsException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus alreadyExists = HttpStatus.CONFLICT;
        final ApiResponse apiResponse = ApiResponse.builder()
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .httpStatusCode(alreadyExists.value())
                .httpStatus(alreadyExists)
                .message(exception.getMessage())
                .path(httpServletRequest.getRequestURI())
                .build();

        return new ResponseEntity<>(apiResponse, alreadyExists);
    }

    @ExceptionHandler(value = IllnessOptionIsNotSelectedException.class)
    public ResponseEntity<ApiResponse> handleIllnessOptionIsNotSelectedRequestException(final IllnessOptionIsNotSelectedException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus notAcceptable = HttpStatus.NOT_ACCEPTABLE;
        final ApiResponse apiResponse = ApiResponse.builder()
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .httpStatusCode(notAcceptable.value())
                .httpStatus(notAcceptable)
                .message(exception.getMessage())
                .path(httpServletRequest.getRequestURI())
                .build();

        return new ResponseEntity<>(apiResponse, notAcceptable);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidRequestException(final MethodArgumentNotValidException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        final ApiResponse apiResponse = ApiResponse.builder()
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .httpStatusCode(badRequest.value())
                .httpStatus(badRequest)
                .message(exception.getMessage())
                .path(httpServletRequest.getRequestURI())
                .build();

        return new ResponseEntity<>(apiResponse, badRequest);
    }

    @ExceptionHandler(value = PdfGenerateException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidRequestException(final PdfGenerateException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        final ApiResponse apiResponse = ApiResponse.builder()
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .httpStatusCode(badRequest.value())
                .httpStatus(badRequest)
                .message(exception.getMessage())
                .path(httpServletRequest.getRequestURI())
                .build();

        return new ResponseEntity<>(apiResponse, badRequest);
    }
}
