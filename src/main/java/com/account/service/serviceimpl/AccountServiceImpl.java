package com.account.service.serviceimpl;
import com.account.service.dto.Customer;
import com.account.service.entities.Account;
import com.account.service.exception.AccountNotFound;
import com.account.service.exception.CustomerNotFoundException;
import com.account.service.exception.SomethingWentWrong;
import com.account.service.repository.AccountRepository;
import com.account.service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AccountRepository accountRepository;
    @Override
    public Account addMoney(String customerId, BigDecimal accountBalance) {
        try {
            Customer customer = restTemplate.getForObject("http://CUSTOMER-SERVICE/customer/getsinglecustomer/" + customerId, Customer.class);
            if (customer == null) {
                throw new CustomerNotFoundException("Error while retriving customer info");
            }

            Account account = accountRepository.findByAccountNo(customer.getAccountNo());
            if (account != null) {
                BigDecimal existingBalance = account.getAccountBalance();
                BigDecimal result = existingBalance.add(accountBalance);
                account.setAccountBalance(result);
                accountRepository.save(account);

            } else {
                account = new Account();
                account.setAccountNo(customer.getAccountNo());
                account.setAccountBalance(accountBalance);
                accountRepository.save(account);
            }
            return account;


        }catch(HttpClientErrorException.NotFound | HttpClientErrorException.BadRequest e)
        {
            throw new CustomerNotFoundException("error while retriving cutomer info");
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new SomethingWentWrong("An error occured while processing the request");

        }

    }


    @Override
    public String withdrawMoney(String customerId, BigDecimal accountBalance) {
        try{
            Customer customer=restTemplate.getForObject("http://CUSTOMER-SERVICE/customer/getsinglecustomer/"+customerId, Customer.class);
            Account account=accountRepository.findByAccountNo(customer.getAccountNo());
            BigDecimal result= BigDecimal.valueOf(0);
            if(account==null)
            {
                throw new AccountNotFound("Account not found");
            }

            if(account!=null) {
                BigDecimal existingBalance = account.getAccountBalance();
                result = existingBalance.subtract(accountBalance);
                int ans=result.compareTo(BigDecimal.ZERO);
                if(ans>=0)
                {
                    account.setAccountBalance(result);
                    accountRepository.save(account);
                }
                else{
                    return "you dont have sufficent balance";
                }

            }
            return "your money is withdraw and your current balance is"+account.getAccountBalance();

        }
        catch(HttpClientErrorException.NotFound | HttpClientErrorException.BadRequest e)
        {
            throw new CustomerNotFoundException("error while retriving cutomer info");
        }
        catch(AccountNotFound e)
        {
            throw new AccountNotFound(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new SomethingWentWrong("An error occured while processing the request");

        }

    }
    @Override
    public Customer getAccountDetails(String customerId)  {
        try{
            Customer customer=restTemplate.getForObject("http://CUSTOMER-SERVICE/customer/getsinglecustomer/"+customerId, Customer.class);
            Account account=accountRepository.findByAccountNo(customer.getAccountNo());
            if(account==null)
            {
               throw  new AccountNotFound("Account not found");
            }
            customer.setAccountBalance(account.getAccountBalance());
            return customer;
        }
        catch(HttpClientErrorException.NotFound | HttpClientErrorException.BadRequest e)
        {
            throw new CustomerNotFoundException("error while retriving cutomer info");
        }
        catch (AccountNotFound e)
        {
            throw new AccountNotFound(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new SomethingWentWrong("An error occured while processing the request");

        }
    }

    @Override
    public String deleteAccount(Long accountNumber) {
        //Customer customer=restTemplate.getForObject("http://localhost:8081/customer/getsinglecustomer/"+customerId, Customer.class);

            Account account=accountRepository.findByAccountNo(accountNumber);

            if(account!=null)
            {
                accountRepository.delete(account);
                return "account is successfully deleted";
            }
            else{
                return "there is no account";
            }


    }


}
