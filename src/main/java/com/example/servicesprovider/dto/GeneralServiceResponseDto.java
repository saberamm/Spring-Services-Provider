package com.example.servicesprovider.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeneralServiceResponseDto {

    private Long id;

    private String serviceName;
}
