package com.customer.service.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message)
    {
        super(message);
    }

}