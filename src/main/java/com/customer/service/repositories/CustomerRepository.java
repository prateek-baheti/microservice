package com.customer.service.repositories;

import com.customer.service.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer ,String> {
    Customer findByCustomerEmailAndPhoneNumber(String customerEmail,String phoneNumber);


}
