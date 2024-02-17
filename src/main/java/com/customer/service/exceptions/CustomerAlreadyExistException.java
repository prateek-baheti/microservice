package com.customer.service.exceptions;

import com.customer.service.entities.Customer;

public class CustomerAlreadyExistException extends RuntimeException{
    public CustomerAlreadyExistException(String message)
    {
        super(message);
    }
}
