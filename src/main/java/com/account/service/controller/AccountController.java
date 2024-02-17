package com.account.service.controller;

import com.account.service.dto.Customer;
import com.account.service.dto.MoneyRequest;
import com.account.service.entities.Account;
import com.account.service.service.AccountService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    private Logger logger= LoggerFactory.getLogger(AccountController.class);

    @PostMapping("/addMoney/{customerId}")
    public ResponseEntity<Account> addMoney(@PathVariable String customerId,@Valid @RequestBody MoneyRequest moneyRequest)
    {
        BigDecimal accountBalance=moneyRequest.getAccountBalance();

         Account accountInfo=accountService.addMoney(customerId,accountBalance);
         return ResponseEntity.status(HttpStatus.ACCEPTED).body(accountInfo);
    }

    @PutMapping("/withdrawMoney/{customerId}")
    public ResponseEntity<String> withdrawMoney(@PathVariable String customerId,@Valid @RequestBody MoneyRequest moneyRequest)
    {
        BigDecimal accountBalance=moneyRequest.getAccountBalance();

        String accountInfo=accountService.withdrawMoney(customerId,accountBalance);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(accountInfo);
    }

    @GetMapping("/getaccountdetails/{customerId}")
    @CircuitBreaker(name= "customerBreaker",fallbackMethod = "CustomerFallBack")
    public ResponseEntity<Customer> getAccountDetails(@PathVariable String customerId){
        Customer customer=accountService.getAccountDetails(customerId);
        return ResponseEntity.ok(customer);
    }
    public ResponseEntity<Customer> CustomerFallBack(String customerId,Exception e)
    {
        logger.info("fallback is exceuted as service is down",e.getMessage());
        Customer customer= Customer.builder().customerEmail("prate@gmail.com").customerName("prats").accountNo(23434344L).accountBalance(BigDecimal.valueOf(6356656)).build();
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }

    @DeleteMapping("/deleteaccount/{accountNumber}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long accountNumber)
    {
        String info=accountService.deleteAccount(accountNumber);
        return ResponseEntity.ok(info);
    }




}
