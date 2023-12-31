package com.example.servicesprovider.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientResponseDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String userName;

    private LocalDate birthDate;

    private String email;

    private Double clientCredit;

    private String phoneNumber;

    private String nationalCode;
}
