package com.example.servicesprovider.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientResponseDto {

    private Long id;

    @NotNull(message = "Name can not be null")
    private String firstName;

    @NotNull(message = "LastName can not be null")
    private String lastName;

    @NotNull(message = "userName can not be null")
    private String userName;

    @NotNull(message = "BirthDate cannot be null")
    private LocalDate birthDate;

    @NotNull(message = "Email cannot be null")
    @Email
    private String email;

    private Double clientCredit;

    @NotNull(message = "PhoneNumber can not be null")
    private String phoneNumber;

    @NotNull(message = "NationalCode can not be null")
    @Size(min = 10, max = 10, message = "NationalCode  must have 10 digits")
    private String nationalCode;
}
