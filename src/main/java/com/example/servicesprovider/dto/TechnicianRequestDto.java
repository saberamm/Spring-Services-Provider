package com.example.servicesprovider.dto;

import com.example.servicesprovider.model.enumeration.TechnicianStatus;
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
public class TechnicianRequestDto {

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

    @NotNull(message = "technicianStatus cannot be null")
    private TechnicianStatus technicianStatus;

    private byte[] technicianPhoto;

    private Double technicianCredit;

    private Double negativeScore;

    private Double overallScore;

    @Size(min = 11, max = 11, message = "PhoneNumber  must have 11 digits")
    private String phoneNumber;

    @NotNull(message = "NationalCode cannot be null")
    @Size(min = 10, max = 10, message = "NationalCode  must have 10 digits")
    private String nationalCode;

    @Size(min = 1, max = 200, message = "About me must be between 1 to 200")
    private String aboutMe;
}
