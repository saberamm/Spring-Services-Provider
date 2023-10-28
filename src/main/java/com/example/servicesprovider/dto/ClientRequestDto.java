package com.example.servicesprovider.dto;

import jakarta.validation.constraints.Email;
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
public class ClientRequestDto {

    @NotNull(message = "Name can not be null")
    private String firstName;

    @NotNull(message = "LastName can not be null")
    private String lastName;

    @NotNull(message = "userName can not be null")
    private String userName;

    @NotNull(message = "Password cannot be null")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,50}$",
            message = "Password must contain 1 digit, 1 lowercase letter, 1 uppercase letter,1 special character, And at least 8 characters.")
    private String password;

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