package com.account.service.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message)
    {
        super(message);
    }

}
