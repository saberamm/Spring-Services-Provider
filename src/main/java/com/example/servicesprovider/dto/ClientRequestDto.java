package com.example.servicesprovider.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientRequestDto {

    private Long id;

    private String firstName;

    private String lastName;

    @NotNull(message = "userName can not be null")
    private String userName;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,50}$",
            message = "Password must contain 1 digit, 1 lowercase letter, 1 uppercase letter,1 special character, And at least 8 characters.")
    private String password;

    private LocalDate birthDate;

    @NotNull(message = "Email cannot be null")
    @Email
    private String email;

    private Double clientCredit;

    private String phoneNumber;

    @Size(min = 10, max = 10, message = "NationalCode  must have 10 digits")
    private String nationalCode;
}