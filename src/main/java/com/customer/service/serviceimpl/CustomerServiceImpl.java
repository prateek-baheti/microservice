package com.customer.service.serviceimpl;
import com.customer.service.dto.CustomerDto;
import com.customer.service.entities.Customer;
import com.customer.service.exceptions.CustomerAlreadyExistException;
import com.customer.service.exceptions.CustomerNotFoundException;
import com.customer.service.exceptions.ResourceNotFoundException;
import com.customer.service.exceptions.SomethingWentWrong;
import com.customer.service.repositories.CustomerRepository;
import com.customer.service.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;


@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private RestTemplate restTemplate;

    private Logger logger= LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public Customer createCustomer(Customer customer) {

       try{
           Customer customeInfo= customerRepository.findByCustomerEmailAndPhoneNumber(customer.getCustomerEmail(),customer.getPhoneNumber());
           if(!ObjectUtils.isEmpty(customeInfo))
           {
               throw new CustomerAlreadyExistException("Customer is already exist");
           }
           Random random=new Random();
           String randomCustomerId=UUID.randomUUID().toString();//generating unique id
           long random10DidigitNumber= (long) (1000000000+random.nextDouble() *9000000000L);
           customer.setCustomerId(randomCustomerId);
           customer.setAccountNo(random10DidigitNumber);
           return customerRepository.save(customer);
       }catch (SomethingWentWrong e)
       {
           e.printStackTrace();
           throw new SomethingWentWrong("An error occured while processing the request");
       }
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(String customerId) {
        Customer customer= customerRepository.findById(customerId).orElseThrow(()->new ResourceNotFoundException("Customer with given id is not found on server"+customerId));
        return customer;
    }

    @Override
    public String deleteCustomer(String customerId) {

        try {
            Customer customer=customerRepository.findById(customerId).orElseThrow(()->new ResourceNotFoundException("Customer with given id is not found on server"+customerId));
            ResponseEntity<String> responseEntity=restTemplate.exchange("http://ACCOUNT-SERVICE/account/deleteaccount/{accountNo}",
                    HttpMethod.DELETE,
                    null,
                    String.class,
                    customer.getAccountNo());
            String ans=responseEntity.getBody();
            if("account is successfully deleted".equals(ans)||"there is no account".equals(ans))
            {
                customerRepository.delete(customer);
                return "customer deleted succefully";
            }
            else {
                return "there is some problem while deleteing customer";
            }
        }
        catch (ResourceNotFoundException e)
        {
            throw new ResourceNotFoundException(e.getMessage());
        }
        catch(HttpClientErrorException.NotFound | HttpClientErrorException.BadRequest e)
        {
            throw new CustomerNotFoundException("error while retriving cutomer info");
        }
        catch (Exception e)
        {
            throw new SomethingWentWrong("An error occured while processing the request");
        }


    }

    @Override
    public Customer updateCustomer(String customerId, CustomerDto customerDto) {
        try{
            Customer existingCustomer=customerRepository.findById(customerId).orElseThrow(()->new ResourceNotFoundException("customer id is not found"));
            Optional.ofNullable(customerDto.getCustomerName()).ifPresent(existingCustomer::setCustomerName);
            Optional.ofNullable(customerDto.getCustomerEmail()).ifPresent(existingCustomer::setCustomerEmail);
            Optional.ofNullable(customerDto.getPhoneNumber()).ifPresent(existingCustomer::setPhoneNumber);
            Optional.ofNullable(customerDto.getAddress()).ifPresent(existingCustomer::setAddress);
            Customer updatedCustomer=customerRepository.save(existingCustomer);
            return updatedCustomer;
        }catch (Exception e)
        {
            throw new SomethingWentWrong("An error occured while processing the request");
        }
    }
}
