package org.ni.recruitservice.exception.handler;


import org.hibernate.exception.ConstraintViolationException;
import org.ni.recruitservice.exception.ApiException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity handleApiException(Exception ex, WebRequest request) {
        if(ex instanceof ApiException) {
            return ResponseEntity.status(((ApiException)ex).getCode()).contentType(MediaType.APPLICATION_JSON).build();
        } else if(ex instanceof  ConstraintViolationException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).build();
        }else if(ex instanceof DataIntegrityViolationException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).build();
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).build();
        }
    }
}