package com.account.service.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MoneyRequest {
    @Min(value = 0,message = "Amount must be non-negative")
    private BigDecimal accountBalance;
}

