package com.account.service.service;

import com.account.service.dto.Customer;
import com.account.service.entities.Account;


import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    Account addMoney(String customerId, BigDecimal accountBalance);


    String withdrawMoney(String customerId, BigDecimal accountBalance);

    //Account getAccountByCustomerId(String customerId);
     Customer getAccountDetails(String customerId);


    String deleteAccount(Long accountNumber);
}
