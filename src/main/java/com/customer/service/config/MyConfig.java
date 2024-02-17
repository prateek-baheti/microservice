package com.customer.service.config;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyConfig {

    @Bean
    @LoadBalanced//ek sai jyada instance ho toh usko distribute karna services kai portno yaha host ka na use karke name ko use karta hai8081->ACCOUNT-SERVICE
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }

    @Bean
    public UserDetailsService userDetailsService()
    {
        UserDetails user=User.builder().username("prateek").password(passwordEncoder().encode("abc")).roles("ADMIN").build();
        UserDetails user1=User.builder().username("prateek123").password(passwordEncoder().encode("abc")).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user,user1);
    }
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }


}
