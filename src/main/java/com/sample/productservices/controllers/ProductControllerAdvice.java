package com.sample.productservices.controllers;

import com.sample.productservices.dtos.ExceptionDto;
import com.sample.productservices.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProductControllerAdvice {
    @ExceptionHandler(ProductNotFoundException.class)
    //@ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<ExceptionDto> handleProductNotFoundException(ProductNotFoundException productNotFoundException) {
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .status("Failure")
                .message(productNotFoundException.getMessage())
                .build();
        return new ResponseEntity<>(exceptionDto,HttpStatus.NOT_FOUND);
    }
}
