package com.account.service.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    private String customerId;
    @Column(length = 20)
    private String customerName;
    private String customerEmail;
    private String phoneNumber;
    private String address;
    private Long accountNo;
    private BigDecimal accountBalance;

}