package com.customer.service.exceptions;

public class SomethingWentWrong extends RuntimeException{
    public SomethingWentWrong(String message)
    {
        super(message);
    }
}
