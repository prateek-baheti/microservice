package com.customer.service.exceptions;

import com.customer.service.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundException ex)
    {
        String message=ex.getMessage();
        ApiResponse response=ApiResponse.builder().message(message).build();
        return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(CustomerAlreadyExistException.class)
    public ResponseEntity<ApiResponse> handlerCustomerAlreadyExistException(CustomerAlreadyExistException ex)
    {
        String message=ex.getMessage();
        ApiResponse response=ApiResponse.builder().message(message).build();
        return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerCustomerNotFoundException(CustomerNotFoundException ex)
    {
        String message=ex.getMessage();
        ApiResponse response=ApiResponse.builder().message(message).build();
        return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(SomethingWentWrong.class)
    public ResponseEntity<ApiResponse> handlerSomethingWentWrongException(SomethingWentWrong ex)
    {
        String message=ex.getMessage();
        ApiResponse response=ApiResponse.builder().message(message).build();
        return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex)
    {
        return ResponseEntity.badRequest().body("validation errors "+ex.getBindingResult().getFieldError().getDefaultMessage());
    }
}
