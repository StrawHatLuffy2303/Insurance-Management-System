package com.ims.insurance_management_system.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private MyErrorDetails createErrorDetails(Exception e, WebRequest req) {
        MyErrorDetails error = new MyErrorDetails();
        error.setTimestamp(LocalDateTime.now());
        error.setMessage(e.getMessage());
        error.setDetails(req.getDescription(false));
        return error;
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<MyErrorDetails> userExceptionHandler(UserException ue, WebRequest req) {
        MyErrorDetails error = createErrorDetails(ue, req);
        return new ResponseEntity<MyErrorDetails>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<MyErrorDetails> loginExceptionHandler(LoginException le, WebRequest req) {
        MyErrorDetails error = createErrorDetails(le, req);
        return new ResponseEntity<MyErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClaimException.class)
    public ResponseEntity<MyErrorDetails> claimExceptionHandler(ClaimException ce, WebRequest req) {
        MyErrorDetails error = createErrorDetails(ce, req);
        return new ResponseEntity<MyErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<MyErrorDetails> clientExceptionHandler(ClientException ce, WebRequest req) {
        MyErrorDetails error = createErrorDetails(ce, req);
        return new ResponseEntity<MyErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PolicyException.class)
    public ResponseEntity<MyErrorDetails> policyExceptionHandler(PolicyException pe, WebRequest req) {
        MyErrorDetails error = createErrorDetails(pe, req);
        return new ResponseEntity<MyErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MyErrorDetails> otherExceptionHandler(Exception e, WebRequest req) {
        MyErrorDetails error = createErrorDetails(e, req);
        return new ResponseEntity<MyErrorDetails>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
