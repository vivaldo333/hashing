package com.ks.hashing.api.handler;

import com.ks.hashing.dto.ErrorResponseDto;
import com.ks.hashing.exception.ProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;

@ControllerAdvice
@Slf4j
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity<ErrorResponseDto> handleValidationExceptions(ValidationException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = getErrorResponse(ex, HttpStatus.BAD_REQUEST);
        log.error("Processing error appeared", ex);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProcessingException.class)
    public final ResponseEntity<ErrorResponseDto> handleProcessingExceptions(ProcessingException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = getErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        log.error("Processing error appeared", ex);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = getErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        log.error("General error appeared", ex);
        return handleExceptionInternal(ex, errorResponseDto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private <T extends Exception> ErrorResponseDto getErrorResponse(T ex, HttpStatus httpStatus) {
        return ErrorResponseDto.builder()
                .code(httpStatus.value())
                .message(ex.getMessage())
                .build();
    }
}
