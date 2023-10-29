package com.example.servicesprovider.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubServiceResponseDto {

    private Long id;

    private String subServiceName;

    private Double basePrice;

    private String description;
}
