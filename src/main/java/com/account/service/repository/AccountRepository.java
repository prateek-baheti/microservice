package com.account.service.repository;

import com.account.service.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface AccountRepository extends JpaRepository<Account,String> {

    //Account findByCustomerId(String customerId);
    Account findByAccountNo(Long accountNo);

}
