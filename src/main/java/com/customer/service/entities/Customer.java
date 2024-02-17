package com.customer.service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Customer {
    @Id
    private String customerId;
    @Column(length = 20)
    @NotNull(message = "customer name cannot be empty")
    @NotBlank(message = "customer name cannot be empty")
    private String customerName;
    @Email(message=" Please provide a valid email address")
    private String customerEmail;
    @Size(min=10,max=10,message = "Phone no must be exactly 10 digits")
    private String phoneNumber;
    @NotBlank(message = "adress cannot be empty")
    @Size(max = 255,message = "Adress cannot exceed 255 characters")
    private String address;
    private Long accountNo;
    //@Transient is used when we dont want to save in database
//    @Transient
//    private Account account;


}
