package com.example.servicesprovider.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreditCardRequestDto {

    private Long id;

    @NotNull(message = "creditCardNumber cannot be null")
    @Size(min = 16, max = 16, message = "creditCardNumber must be 16 characters")
    private String creditCardNumber;

    @NotNull(message = "expire date cannot be null")
    private LocalDate expireDate;

    @Size(min = 4, max = 4, message = "cvv2 must be 4 characters")
    @NotNull(message = "cvv2 cannot be null")
    private String cvv2;

    @Pattern(regexp = "^[0-9]{8}$", message = "secondPassword must be 8 digit")
    @NotNull(message = "secondPassword cannot be null")
    private String secondPassword;
}
