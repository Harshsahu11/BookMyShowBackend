package com.bms.BookMyShow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex,
                                                       WebRequest request){

        ErrorResponse errordetail = new ErrorResponse(
                new Date(),
                HttpStatus.NOT_FOUND.value(),
            "Not Found",
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errordetail,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(SeatUnavailableException.class)
    public ResponseEntity<?> SeatUnavailableException(SeatUnavailableException ex,
                                                       WebRequest request){

        ErrorResponse errordetail = new ErrorResponse(
                new Date(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad request",
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errordetail,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> SeatUnavailableException(Exception ex,
                                                      WebRequest request){

        ErrorResponse errordetail = new ErrorResponse(
                new Date(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errordetail,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
