package com.example.servicesprovider.dto;

import com.example.servicesprovider.model.enumeration.AdminPosition;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminRequestDto {

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


    @NotNull(message = "position can not be null")
    private AdminPosition position;
}
