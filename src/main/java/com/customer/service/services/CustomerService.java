package com.customer.service.services;

import com.customer.service.dto.CustomerDto;
import com.customer.service.entities.Customer;

import java.util.List;

public interface CustomerService {


    Customer createCustomer(Customer customer);

    List<Customer> getAllCustomer();

    Customer getCustomer(String customerId);

    String deleteCustomer(String customerId);

    Customer updateCustomer(String customerId,CustomerDto customerDto);


}
