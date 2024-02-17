package com.customer.service.dto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {


    @Column(length = 20)
    private String customerName;
    @Email(message="Please provide a valid email address")
    private String customerEmail;
    @Size(min=10,max=10,message = "Phone no must be exactly 10 digits")
    private String phoneNumber;
    @Size(max = 255,message = "Adress cannot exceed 255 characters")
    private String address;

}

