package com.giansiccardi.product.handler;


import com.giansiccardi.product.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(ProductPurchaseException.class)
    public ResponseEntity<String>handle(ProductPurchaseException exp){
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
