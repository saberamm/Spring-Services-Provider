package com.example.servicesprovider.dto;

import com.example.servicesprovider.model.enumeration.AdminPosition;
import com.example.servicesprovider.model.enumeration.TechnicianStatus;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String userName;

    private LocalDate birthDate;

    private String email;

    private AdminPosition position;

    private Double clientCredit;

    private String phoneNumber;

    private String nationalCode;

    private TechnicianStatus technicianStatus;

    private Double technicianCredit;

    private Double negativeScore;

    private Double overallScore;

    private String aboutMe;
}
