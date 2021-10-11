package com.accsin.exeptions;

import java.util.Date;

import com.accsin.models.responses.exeption.ErrorMessage;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AppExeptionsHandler {
    
    @ExceptionHandler(value = UnauthorizedExeption.class)
    public ResponseEntity<Object> unauthorizedExeption(UnauthorizedExeption ex, WebRequest webRequest){
        ErrorMessage errorMessage = ErrorMessage.builder().message(ex.getMessage()).timestamp(new Date()).build();
        return new ResponseEntity<>(errorMessage,new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = ExistEmailExeption.class)
    public ResponseEntity<Object> existEmailExeption(ExistEmailExeption ex,WebRequest webRequest){
        ErrorMessage errorMessage = ErrorMessage.builder().message(ex.getMessage()).timestamp(new Date()).build();
        return new ResponseEntity<>(errorMessage,new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}