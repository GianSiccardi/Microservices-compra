package com.giansiccardi.client.handler;

import com.giansiccardi.client.exception.ClientNotFOundExepction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(ClientNotFOundExepction.class)
    public ResponseEntity<String>handle(ClientNotFOundExepction exp){
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(exp.getMsg());
}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>error(MethodArgumentNotValidException exp){
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
