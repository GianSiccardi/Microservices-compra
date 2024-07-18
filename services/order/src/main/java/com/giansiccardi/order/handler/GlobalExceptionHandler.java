package com.giansiccardi.order.handler;


import com.giansiccardi.order.exception.BusniessException;

import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.KafkaException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashMap;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
@ExceptionHandler(BusniessException.class)
    public ResponseEntity<String>handle(BusniessException exp){
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(exp.getMessage());
}



    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String>handle(EntityNotFoundException exp){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exp.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>handle(MethodArgumentNotValidException exp){
      var erros= new HashMap<String,String>();
      exp.getBindingResult().getAllErrors()
              .forEach(error->{
                  var fieldNme=((FieldError)error).getField();
                  var errorMessage= error.getDefaultMessage();
                  erros.put(fieldNme,errorMessage);
              });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(erros));
    }


}
