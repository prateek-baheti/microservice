package com.account.service.exception;

public class AccountNotFound extends RuntimeException{
    public AccountNotFound(String message)
    {
        super(message);
    }
}
