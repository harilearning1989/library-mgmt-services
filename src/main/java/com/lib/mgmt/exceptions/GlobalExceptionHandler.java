package com.lib.mgmt.exceptions;

import com.lib.mgmt.exceptions.auth.InvalidUserCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /*@ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNoRecordFoundException(NoSuchElementException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        return errorResponse;
    }*/

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Object> handleExceptions(NoSuchElementException exception, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(exception.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
        return entity;
    }

    @ExceptionHandler(GlobalMessageException.class)
    @ResponseBody
    public ResponseEntity<Object> studentNotFoundException(
            GlobalMessageException exception, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setStatus(exception.getStatus().value());
        return new ResponseEntity<>(errorResponse,exception.getStatus());
    }

    @ExceptionHandler(RequestRejectedException.class)
    @ResponseBody
    public ResponseEntity<Object> requestRejectionException(RequestRejectedException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidUserCredentialsException.class)
    @ResponseBody
    public ResponseEntity<Object> invalidUserCredentialsException(InvalidUserCredentialsException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(errorResponse,HttpStatus.UNAUTHORIZED);
    }

}
