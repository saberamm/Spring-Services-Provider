package com.example.servicesprovider.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreditCardResponseDto {

    private Long id;

    private String creditCardNumber;

    private LocalDate expireDate;

    private String cvv2;

    private String secondPassword;

}
