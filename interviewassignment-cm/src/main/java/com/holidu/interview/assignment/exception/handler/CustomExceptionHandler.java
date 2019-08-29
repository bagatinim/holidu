package com.holidu.interview.assignment.exception.handler;

import com.holidu.interview.assignment.exception.DataFetchingException;
import com.holidu.interview.assignment.exception.EmptyResultException;
import com.holidu.interview.assignment.exception.UnexpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(DataFetchingException.class)
    protected ResponseEntity handleDataFetchingException(DataFetchingException ex) {
        LOGGER.error("An error happened while fetching external data.", ex);
        return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnexpectedException.class)
    protected ResponseEntity handleUnexpectedException(UnexpectedException ex) {
        LOGGER.error("An unpredicted error happened while processing the request.", ex);
        return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmptyResultException.class)
    protected ResponseEntity handleEmptyResultException(EmptyResultException ex) {
        LOGGER.error("An empty or non-ok response was returned from the external service.", ex);
        return new ResponseEntity(ex.getMessage(), HttpStatus.NO_CONTENT);
    }

}
