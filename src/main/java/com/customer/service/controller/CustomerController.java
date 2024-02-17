package com.customer.service.controller;

//import com.customer.service.dto.CustomerDto;
import com.customer.service.dto.CustomerDto;
import com.customer.service.entities.Customer;
import com.customer.service.services.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/createcustomer")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer)
    {
        Customer customerData=customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerData);

    }

    @GetMapping("/getsinglecustomer/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String customerId)
    {
        Customer getCustomerData=customerService.getCustomer(customerId);
        return ResponseEntity.ok(getCustomerData);
    }

    @GetMapping("/getallcustomer")
    public ResponseEntity<List<Customer>> getAllCustomer()
    {
        List<Customer> allCustomer=customerService.getAllCustomer();
        return ResponseEntity.ok(allCustomer);
    }
    @DeleteMapping("/deletecustomer/{customerId}")
    public ResponseEntity<String> deleteCustomer( @PathVariable String customerId)
    {
        String getData=customerService.deleteCustomer(customerId);
        return ResponseEntity.ok(getData);
    }
    @PutMapping("/updatecustomer/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String customerId,@Valid @RequestBody CustomerDto customerDto)
    {
        Customer updatedCustomer=customerService.updateCustomer(customerId,customerDto);
        return ResponseEntity.ok(updatedCustomer);
    }

}
