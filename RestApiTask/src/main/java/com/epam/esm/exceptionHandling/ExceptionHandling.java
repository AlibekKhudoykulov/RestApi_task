package com.epam.esm.exceptionHandling;

import com.epam.esm.exceptionHandling.exceptions.ClientError;
import com.epam.esm.exceptionHandling.exceptions.NoContentException;
import com.epam.esm.exceptionHandling.exceptions.NotFoundException;
import com.epam.esm.payload.ExceptionResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(NotFoundException.class)
    public final HttpEntity<?> handleNotFoundException(NotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), 40401);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoContentException.class)
    public final HttpEntity<?> handleNoContentException(NoContentException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), 20401);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(ClientError.class)
    public final HttpEntity<?> handleClientSideException(ClientError ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), 40901);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

}
