package com.example.servicesprovider.dto;

import com.example.servicesprovider.model.enumeration.AdminPosition;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminResponseDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String userName;

    private LocalDate birthDate;

    private String email;

    private AdminPosition position;
}
