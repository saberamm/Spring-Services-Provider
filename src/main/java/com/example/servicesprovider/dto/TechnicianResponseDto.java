package com.example.servicesprovider.dto;

import com.example.servicesprovider.model.enumeration.TechnicianStatus;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TechnicianResponseDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String userName;

    private LocalDate birthDate;

    private String email;

    private TechnicianStatus technicianStatus;


    private Double technicianCredit;

    private Double negativeScore;

    private Double overallScore;

    private String phoneNumber;

    private String nationalCode;

    private String aboutMe;
}
